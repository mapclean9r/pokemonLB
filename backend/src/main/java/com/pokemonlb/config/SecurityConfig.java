package com.pokemonlb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security for a read-only API protected by an API key.
 *
 * <ul>
 *   <li>{@code GET /api/v1/**} needs the key in the {@code X-API-Key} header
 *       ({@link ApiKeyFilter}). Missing or wrong key: {@code 401}.
 *   <li>Every other method or path is refused, so nothing can write to the data even if a non-GET
 *       endpoint is added by mistake: {@code 403} with a valid key, {@code 401} without one.
 *   <li>Stateless: no session and no cookies. CSRF protection is off because it only guards
 *       cookie-based sessions, and the API has none.
 *   <li>Spring Security's default response headers stay on ({@code X-Content-Type-Options},
 *       {@code X-Frame-Options}, {@code Cache-Control}, ...).
 * </ul>
 *
 * <p>The key is {@code pokemonlb.api-key} in {@code application.yml}, overridable with the
 * {@code POKEMON_API_KEY} environment variable. Controller tests use this config through
 * {@code ApiKeyTestConfig}, which also sends the key with every test request.
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${pokemonlb.api-key}") String apiKey)
            throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(new ApiKeyFilter(apiKey), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(errors -> errors.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, ApiUrls.API + "/**").authenticated()
                        // Spring's error page: renders the 400/404 bodies for requests that got in.
                        .requestMatchers("/error").permitAll()
                        .anyRequest().denyAll())
                .build();
    }

    /**
     * No user accounts: the only client is identified by the API key. Declaring an empty user store
     * stops Spring Boot from creating a default "user" with a generated password at startup.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
