package dev.advik.InviteTree.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

public class Database {
    private Connection conn;
    private final Logger logger;

    public Database(String path, Logger logger) {
        this.logger = logger;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path + "/invites.db");
        } catch (Exception e) {
            logger.severe("Could not connect to database: " + e.getMessage());
        }
        createTables();
    }

    public void createTables() {
        // Invites table
        // UUID: a unique identifier for the invite | inviter: name of the invitor | invitee: name of the invitee | status: InvitationStatus | invited at: timestamp | accepted at: timestamp
        String sql = "CREATE TABLE IF NOT EXISTS invites (" + "uuid TEXT PRIMARY KEY," + "inviter TEXT," + "invitee TEXT," + "status INTEGER," + "invited_at INTEGER," + "accepted_at INTEGER" + ");";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Could not create invites table: " + e.getMessage());
        }

        // Users table
        // name: username | invite: (references invites.uuid)
        sql = "CREATE TABLE IF NOT EXISTS users (name TEXT PRIMARY KEY, invite TEXT, FOREIGN KEY (invite) REFERENCES invites(uuid));";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Could not create users table: " + e.getMessage());
        }
    }

    

    public void shutdown() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            logger.severe("Error closing database: " + e.getMessage());
        }
    }
}
