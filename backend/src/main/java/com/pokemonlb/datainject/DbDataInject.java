package com.pokemonlb.datainject;

import java.nio.file.Path;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Run this once to fill the database with the Pokémon data. It is separate from the app:
 * starting the app never injects data.
 *
 * <p>If the database already has data, it does nothing. Pass {@code --force} to wipe the data
 * tables and inject everything again. Pass a folder path to use SQL files from somewhere other
 * than the {@code sql/} folder in this package. Run it from the project root (IntelliJ's default).
 *
 * <p>Uses the app's database settings ({@code application.yml}); Flyway creates the tables first
 * if they do not exist yet.
 */
public final class DbDataInject {

    /** The sql/ folder next to this file, relative to the project root. */
    private static final String DEFAULT_SQL_DIR = "src/main/java/com/pokemonlb/datainject/sql";
    private static final String FORCE_FLAG = "--force";

    private DbDataInject() {}

    public static void main(String[] args) throws Exception {
        boolean force = Arrays.asList(args).contains(FORCE_FLAG);
        Path sqlDir = Arrays.stream(args)
                .filter(arg -> !arg.startsWith("--"))
                .findFirst()
                .map(Path::of)
                .orElse(Path.of(DEFAULT_SQL_DIR));

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(Config.class)
                .web(WebApplicationType.NONE)
                .run(args)) {
            new SqlScriptInjector(context.getBean(DataSource.class)).inject(sqlDir, force);
        }
    }

    /**
     * Deliberately not a {@code @Configuration}: this package is inside the app's component scan,
     * and nothing here is a Spring component, so starting the app never runs the injector.
     */
    @EnableAutoConfiguration
    static class Config {}
}
