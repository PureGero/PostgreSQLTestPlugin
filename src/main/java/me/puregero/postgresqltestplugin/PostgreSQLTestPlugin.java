package me.puregero.postgresqltestplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Install PostgreSQL - https://github.com/PureGero/PostgreSQLTestPlugin
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Class not found, let's add it to the classpath
            File postgresqlJarFile = new File("postgresql.jar");
            if (!postgresqlJarFile.isFile()) {
                // The jar file doesn't exist, let's download it
                getLogger().info("Downloading postgresql.jar...");
                String url = "https://jdbc.postgresql.org/download/postgresql-42.3.1.jar";
                try {
                    Files.copy(new URL(url).openStream(), postgresqlJarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                // Load the postgresql jar
                new URLClassLoader(new URL[] {postgresqlJarFile.toURL()}, this.getClass().getClassLoader());
                Class.forName("org.postgresql.Driver");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // Finished installing PostgreSQL

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}