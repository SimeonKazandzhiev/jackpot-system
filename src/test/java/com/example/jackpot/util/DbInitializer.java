package com.example.jackpot.util;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.jackpot.constants.PathConstants.CREATE_TABLE_SCRIPT_PATH;

public class DbInitializer {

    private static String scriptCreate;

    public static Jdbi initializeDatabase() throws IOException {
        Path filePathCreate = Path.of(CREATE_TABLE_SCRIPT_PATH);
        scriptCreate = Files.readString(filePathCreate);

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    public static String getScriptCreate() {
        return scriptCreate;
    }
}
