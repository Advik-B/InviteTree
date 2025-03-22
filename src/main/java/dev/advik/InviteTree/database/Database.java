package dev.advik.InviteTree.database;

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

        // Players table
        // name: username | status: PlayerStatus
        sql = "CREATE TABLE IF NOT EXISTS players (name TEXT PRIMARY KEY, status INTEGER);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.severe("Could not create players table: " + e.getMessage());
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

    public Connection getConn() {
        return conn;
    }

    //    public String CreateInvite(String inviter, String invitee) {
//        String sql = "INSERT INTO invites (uuid, inviter, invitee, status, invited_at, accepted_at) VALUES (?, ?, ?, ?, ?, ?);";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            String uuid = UUID.randomUUID().toString();
//            stmt.setString(1, uuid);
//            stmt.setString(2, inviter);
//            stmt.setString(3, invitee);
//            stmt.setInt(4, 0);
//            stmt.setLong(5, System.currentTimeMillis());
//            stmt.setLong(6, 0);
//            stmt.executeUpdate();
//            return uuid;
//        } catch (Exception e) {
//            logger.severe("Could not create invite: " + e.getMessage());
//        } return null;
//    }
//
//    public void AcceptInvite(String invitee, String InviteCode) {
//        String sql = "UPDATE invites SET status = 1, accepted_at = ? WHERE invitee = ? AND uuid = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setLong(1, System.currentTimeMillis());
//            stmt.setString(2, invitee);
//            stmt.setString(3, InviteCode);
//            stmt.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Could not accept invite: " + e.getMessage());
//        }
//    }
//
//    public boolean InviteExists(String invitee) {
//        String sql = "SELECT * FROM invites WHERE invitee = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, invitee);
//            return stmt.executeQuery().next();
//        } catch (Exception e) {
//            logger.severe("Could not check if invite exists: " + e.getMessage());
//        } return false;
//    }
//
//    public void RejectInvite(String invitee) {
//        String sql = "UPDATE invites SET status = 2 WHERE invitee = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, invitee);
//            stmt.executeUpdate();
//        } catch (Exception e) {
//            logger.severe("Could not reject invite: " + e.getMessage());
//        }
//    }

}
