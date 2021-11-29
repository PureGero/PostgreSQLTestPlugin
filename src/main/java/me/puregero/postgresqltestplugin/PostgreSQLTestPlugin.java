package me.puregero.postgresqltestplugin;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Statement;

public class PostgreSQLTestPlugin extends JavaPlugin {

    private PGConnection connection;

    @Override
    public void onEnable() {
        try {
            PGDataSource ds = new PGDataSource();
            ds.setServerName("localhost");
            ds.setDatabaseName("test");
            ds.setUser("postgres");
            ds.setPassword("password");

            connection = (PGConnection) ds.getConnection();

            PGNotificationListener listener = new PGNotificationListener() {
                public void notification(int processId, String channelName, String payload) {
                    System.out.println("notification = " + payload);
                }
            };

            Statement statement = connection.createStatement();
            statement.execute("LISTEN test");
            statement.close();
            connection.addNotificationListener(listener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}