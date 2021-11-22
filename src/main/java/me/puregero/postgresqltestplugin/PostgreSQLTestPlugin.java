package me.puregero.postgresqltestplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            // Load the driver
            Class.forName("org.postgresql.Driver");
            // Connect to your database
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/testdb",
                    "postgres",
                    "password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}