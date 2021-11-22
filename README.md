# Using PostgreSQL with a Bukkit plugin

## Adding the library to your plugin.yml

Open plugin.yml and add the following to it:

```yml
libraries:
  - org.postgresql:postgresql:42.3.1
```

An exemplar plugin.yml:

```yml
name: MyPlugin
main: myplugin.MyPlugin
libraries:
  - org.postgresql:postgresql:42.3.1
commands:
  balance:
    description: See your balance
    default: true
```

## Load the PostgreSQL driver and connect to your database

```java
@Override
public void onEnable() {
    try {
        // Load the driver
        Class.forName("org.postgresql.Driver");
        // Connect to your database
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb",
                "username",
                "password");
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
```