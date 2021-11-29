package me.puregero.postgresqltestplugin;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLTestPlugin extends JavaPlugin {

    private PGConnection connection;

    @Override
    public void onEnable() {
        try {
            // Create the connection
            PGDataSource ds = new PGDataSource();
            ds.setServerName("localhost");
            ds.setDatabaseName("test");
            ds.setUser("postgres");
            ds.setPassword("password");

            connection = (PGConnection) ds.getConnection();

            // Listen for notifications
            connection.addNotificationListener(new PGNotificationListener() {
                public void notification(int processId, String channelName, String payload) {
                    getLogger().info("notification for " + channelName + ": " + payload);
                    // This is where you'd handle notifications from other servers
                }
            });

            // Listen to the `test` channel
            try (Statement statement = connection.createStatement()) {
                statement.execute("LISTEN test");
            }

            // Send a notification to the `test` channel
            // This will send the notification to every server listening to the `test` channel
            sendNotification("test", "payload goes here");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendNotification(String channel, String payload) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("NOTIFY " + channel + ", '" + payload + "'");
        }
    }

}