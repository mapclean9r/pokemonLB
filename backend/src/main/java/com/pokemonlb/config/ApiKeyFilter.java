package com.pokemonlb.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Signs a request in as the API client when its {@value #HEADER} header holds the configured key.
 * A missing or wrong key just leaves the request anonymous; {@link SecurityConfig} then answers
 * {@code 401}.
 *
 * <p>Not a Spring bean on purpose: Spring Boot would also register a bean filter as a plain servlet
 * filter, running it twice. {@link SecurityConfig} creates it and adds it to the security chain.
 */
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-API-Key";

    private final byte[] expectedKey;

    public ApiKeyFilter(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "pokemonlb.api-key is empty: set POKEMON_API_KEY or the value in application.yml");
        }
        this.expectedKey = apiKey.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String givenKey = request.getHeader(HEADER);
        // Constant-time comparison, so response timing doesn't reveal how much of a guess was right.
        if (givenKey != null && MessageDigest.isEqual(expectedKey, givenKey.getBytes(StandardCharsets.UTF_8))) {
            var authentication = UsernamePasswordAuthenticationToken.authenticated(
                    "api-client", null, List.of(new SimpleGrantedAuthority("ROLE_API_CLIENT")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
