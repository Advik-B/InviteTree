package dev.advik.invitetree.tree;

import dev.advik.invitetree.database.DatabaseSchema;

import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDatabase implements DatabaseSchema {
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
        | access_token | player_name | password |
         */
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
