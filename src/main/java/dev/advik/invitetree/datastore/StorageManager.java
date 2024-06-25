package dev.advik.invitetree.datastore;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageManager {
    private final String url;
    private Connection connection;
    private Logger logger;

    public StorageManager(String url, Logger logger) {
        this.url = url;
        this.logger = logger;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url);
            logger.info("Connected to the database!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database with the URL: " + url, e);
        }
    }

    public void createTables() {
        try {
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Players (" +
                            "access_token UUID PRIMARY KEY, " +
                            "player_name VARCHAR(16), " +
                            "invites TEXT, " + // Using TEXT to store JSON array of UUIDs
                            "invited_by UUID, " +
                            "date_joined TIMESTAMP, " +
                            "last_login TIMESTAMP, " +
                            "ips TEXT, " + // Using TEXT to store JSON array of IP addresses
                            "password_hash VARCHAR(256)" +
                            ")");
            logger.info("Created the Players table!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create the Players table!", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
            logger.info("Disconnected from the database!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to disconnect from the database!", e);
        }
    }

    public void addPlayer(String playerName, String invitedBy, String password, String accessToken) {
        try {
            String passwordHash = hashPassword(password);
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Players (access_token, player_name, invited_by, date_joined, last_login, password_hash) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)");
            stmt.setObject(1, UUID.fromString(accessToken));
            stmt.setString(2, playerName);
            stmt.setObject(3, invitedBy != null ? UUID.fromString(invitedBy) : null);
            stmt.setString(4, passwordHash);
            stmt.executeUpdate();
            logger.info("Added player: " + playerName);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to add player: " + playerName, e);
        }
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(StorageManager.class.getName());
        StorageManager storageManager = new StorageManager("jdbc:db.sqlite3", logger);

        storageManager.connect();
        storageManager.createTables();
        storageManager.addPlayer("Advik", "c56a4180-65aa-42ec-a945-5fd21dec0538", "password", "b79f937c-86f6-44d4-97ed-2e289c0f819a");
        storageManager.disconnect();
    }
}
