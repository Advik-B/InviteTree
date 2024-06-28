package dev.advik.invitetree.tree;

import dev.advik.invitetree.database.DatabaseScaffold;
import dev.advik.invitetree.storage.TRAccessToken;

import java.sql.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDatabase implements DatabaseScaffold {
    String connectionUrl;
    Logger logger;
    Connection dbConn;

    public PlayerDatabase(String dbURL, Logger logger) {
        connectionUrl = dbURL;
        this.logger = logger;
    }

    @Override
    public void createTables() {
        /*
        >>> Player <<<
        | player_name | player_uuid | player_status | access_token | invited_by | invited_at | last_login | password_hash |
        |-------------|-------------|---------------|--------------|------------|------------|------------|--------------|
        | String      | String      | PlayerStatus  | UUID       | String     | ZonedDateTime | ZonedDateTime | String |
         */

        String createPlayerTable = "CREATE TABLE IF NOT EXISTS players (" +
            "player_name TEXT PRIMARY KEY," +
            "player_uuid TEXT NOT NULL," +
            "player_status TEXT NOT NULL," +
            "access_token TEXT REFERENCES access_tokens(token)," +
            "invited_by TEXT," +
            "invited_at TIMESTAMP," +
            "last_login TIMESTAMP," +
            "password_hash TEXT" +
            ");";

        String createAccessTokenTable = "CREATE TABLE IF NOT EXISTS access_tokens (" +
            "token TEXT PRIMARY KEY," +
            "status TEXT NOT NULL," +
            "invited_at TIMESTAMP" +
            "used_at TIMESTAMP" +
            ");";

        executeUpdate(createAccessTokenTable);
        executeUpdate(createPlayerTable);
    }

    @Override
    public void connect() {
        // Connect to the database
        try {
            dbConn = DriverManager.getConnection(connectionUrl);
            logger.info("Connected to database at " + connectionUrl);
        } catch (SQLException e) {
            logger.severe("Failed to connect to database at " + connectionUrl);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String executeQuery(String query, Map<Integer, Object> params) {
        try {
            PreparedStatement stmt = dbConn.prepareStatement(query);
            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            ResultSet rs = stmt.executeQuery();
            logger.log(Level.WARNING, "Query executed: " + query);
            logger.log(Level.WARNING, "Params: " + params);
            logger.log(Level.WARNING, "Result: " + rs.getString(1));
            return rs.getString(1);
        } catch (SQLException e) {
            logger.severe("Failed to execute query: " + query);
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void executeUpdate(String query, Map<Integer, Object> params) {
        try {
            PreparedStatement stmt = dbConn.prepareStatement(query);
            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Failed to execute update: " + query);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String executeQuery(String query) {
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.getString(1);
        } catch (SQLException e) {
            logger.severe("Failed to execute query: " + query);
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void executeUpdate(String query) {
        try {
            Statement stmt = dbConn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.severe("Failed to execute update: " + query);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void executeInsert(String query, Map<Integer, Object> params) {
        try {
            PreparedStatement stmt = dbConn.prepareStatement(query);
            for (Entry<Integer, Object> entry : params.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Failed to execute insert: " + query);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void closeConnection() {
        try {
            dbConn.close();
            logger.info("Closed connection to database at " + connectionUrl);
        } catch (SQLException e) {
            logger.severe("Failed to close connection to database at " + connectionUrl);
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
