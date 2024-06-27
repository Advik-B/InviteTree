package dev.advik.invitetree.tree;

import dev.advik.invitetree.database.AccessTokenStatus;
import dev.advik.invitetree.database.DatabaseSchema;
import dev.advik.invitetree.database.InviteType;

import java.sql.*;
import java.util.Map;
import java.util.UUID;
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
    public String generateAccessToken(InviteType invitorType) {
        return "";
    }

    @Override
    public String generateAccessToken(InviteType invitorType, String invitorName) {
        return "";
    }

    @Override
    public void addPlayer(String playerName, String accessToken, String password) {

    }

    @Override
    public void removePlayer(String playerName) {

    }

    @Override
    public void updatePassword(String playerName, String password) {

    }

    @Override
    public boolean playerExists(String playerName) {
        return false;
    }

    @Override
    public boolean accessTokenExists(String accessToken) {
        return false;
    }

    @Override
    public boolean accessTokenMatchesPlayer(String accessToken, String playerName) {
        return false;
    }

    @Override
    public boolean authenticatePlayer(String playerName, String password) {
        return false;
    }

    @Override
    public String getAccessToken(String playerName) {
        return "";
    }

    @Override
    public AccessTokenStatus getAccessTokenStatus(String accessToken) {
        return null;
    }

    @Override
    public String getInvitorName(String accessToken) {
        return "";
    }

    @Override
    public InviteType getInvitorType(String accessToken) {
        return null;
    }

    @Override
    public void disableAccessToken(String accessToken) {

    }

    @Override
    public void enableAccessToken(String accessToken) {

    }

    @Override
    public void disableAllAccessTokens(String playerName) {

    }

    @Override
    public void enableAllAccessTokens(String playerName) {

    }

    @Override
    public void pauseInvites(String playerName) {

    }

    @Override
    public void resumeInvites(String playerName) {

    }

    @Override
    public void pauseInvites() {

    }

    @Override
    public void resumeInvites() {

    }

    @Override
    public void trackPlayer(String playerName) {

    }

    @Override
    public void untrackPlayer(String playerName) {

    }

    @Override
    public boolean isPlayerTracked(String playerName) {
        return false;
    }

    @Override
    public String[] getTrackedPlayers() {
        return new String[0];
    }

    @Override
    public String[] getPlayerInvites(String playerName) {
        return new String[0];
    }

    @Override
    public Map<String, String> getInvites() {
        return Map.of();
    }

    @Override
    public String generateAccessToken() {
        return "";
    }

    @Override
    public String generateAccessToken(String invitorName, String password) {
        return "";
    }

    @Override
    public void closeConnection() {

    }
}
