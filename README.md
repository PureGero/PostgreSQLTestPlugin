# Using PostgreSQL with a Bukkit plugin

## Adding the library to your plugin.yml

Open plugin.yml and add the following to it:

```yml
libraries:
  - com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9
```

An exemplar plugin.yml:

```yml
name: MyPlugin
main: myplugin.MyPlugin
libraries:
  - com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9
commands:
  balance:
    description: See your balance
    default: true
```

## Adding the dependency to your pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>com.impossibl.pgjdbc-ng</groupId>
        <artifactId>pgjdbc-ng</artifactId>
        <version>0.8.9</version>
    </dependency>
</dependencies>
```

## Connect to your PostgreSQL database

```java
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
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
```

## Listen and send notifications
```java
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
try (Statement statement = connection.createStatement()) {
    statement.execute("NOTIFY test, 'payload goes here'");
}
```