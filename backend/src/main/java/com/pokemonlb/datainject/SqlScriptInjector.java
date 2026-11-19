package com.pokemonlb.datainject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

/**
 * Runs every {@code .sql} file in a folder in file-name order, inside one transaction:
 * if any script fails, nothing is saved.
 */
@Slf4j
@RequiredArgsConstructor
class SqlScriptInjector {

    private final DataSource dataSource;

    void inject(Path sqlDir, boolean force) throws Exception {
        List<Path> scripts = findScripts(sqlDir.toAbsolutePath().normalize());

        try (Connection connection = dataSource.getConnection()) {
            if (!force && hasData(connection)) {
                log.info("Database already has data, nothing to do. Run with --force to wipe and inject again.");
                return;
            }

            connection.setAutoCommit(false);
            try {
                for (Path script : scripts) {
                    long start = System.nanoTime();
                    ScriptUtils.executeSqlScript(connection,
                            new EncodedResource(new FileSystemResource(script), StandardCharsets.UTF_8));
                    log.info("Ran {} in {} ms", script.getFileName(), (System.nanoTime() - start) / 1_000_000);
                }
                connection.commit();
                log.info("Data injection complete: {} scripts committed", scripts.size());
            } catch (Exception e) {
                connection.rollback();
                log.error("Data injection failed, all changes rolled back", e);
                throw e;
            }
        }
    }

    private static boolean hasData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM pokemon)")) {
            return result.next() && result.getBoolean(1);
        }
    }

    private static List<Path> findScripts(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            throw new IllegalStateException("SQL folder not found: " + dir
                    + " (run from the project root, or pass the folder path as an argument)");
        }
        try (Stream<Path> files = Files.list(dir)) {
            List<Path> scripts = files
                    .filter(p -> p.getFileName().toString().endsWith(".sql"))
                    .sorted()
                    .toList();
            if (scripts.isEmpty()) {
                throw new IllegalStateException("No .sql files in " + dir);
            }
            return scripts;
        }
    }
}
