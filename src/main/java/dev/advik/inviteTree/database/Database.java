package dev.advik.inviteTree.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Database {
    private String connectionUrl;
    private Connection connection;
    private Logger log;

    public Database(String datafolder, Logger logger) {
        log = logger;
        connectionUrl = "jdbc:sqlite:" + datafolder + "/players.db";
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            if (connection != null) {
                log.info("Connected to the database");
                ping();
                createTables();
            }
        } catch (SQLException e) {
            log.throwing("Database", "connect", e);
        }
    }

    private void ping() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT 1");
            statement.close();
        } catch (SQLException e) {
            log.throwing("Database", "ping", e);
        }
    }

    public void shutdown() {
        try {
            if (connection != null) {
                connection.close();
                log.info("Disconnected from the database");
            }
        } catch (SQLException e) {
            log.throwing("Database", "shutdown", e);
        }
    }

    private void createTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS players (" +
                    "UUID TEXT PRIMARY KEY," +
                    "invited_by TEXT NOT NULL FOREIGN KEY REFERENCES players(UUID)," +
                    "status TEXT NOT NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "INSERT INTO players (UUID, invited_by, status) VALUES ('1234', '5678', 'accepted')");
            statement.close();
        } catch (SQLException e) {
            log.throwing("Database", "createTables", e);
        }
    }
}
