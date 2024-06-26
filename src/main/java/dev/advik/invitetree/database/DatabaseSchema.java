package dev.advik.invitetree.database;

import java.util.Map;

public interface DatabaseSchema {

    public void setConnectionUrl(String url);

    public void createTables();

    public void connect();

    public String generateAccessToken(InviteType invitorType); // This assumes that the invitorName is not needed because invitor is CONSOLE

    public String generateAccessToken(InviteType invitorType, String invitorName);

    public void addPlayer(String playerName, String accessToken, String password);

    public void removePlayer(String playerName);

    public void updatePassword(String playerName, String password);

    public boolean playerExists(String playerName);

    public boolean accessTokenExists(String accessToken);

    public boolean accessTokenMatchesPlayer(String accessToken, String playerName);

    public boolean authenticatePlayer(String playerName, String password);

    public String getAccessToken(String playerName);

    public AccessTokenStatus getAccessTokenStatus(String accessToken);

    public String getInvitorName(String accessToken);

    public InviteType getInvitorType(String accessToken);

    public void disableAccessToken(String accessToken);

    public void enableAccessToken(String accessToken);

    public void disableAllAccessTokens(String playerName);

    public void enableAllAccessTokens(String playerName);

    public void pauseInvites(String playerName);

    public void resumeInvites(String playerName);

    public void pauseInvites();

    public void resumeInvites();

    public void trackPlayer(String playerName);

    public void untrackPlayer(String playerName);

    public boolean isPlayerTracked(String playerName);

    public String[] getTrackedPlayers();

    public String[] getPlayerInvites(String playerName);

    public Map<String, String> getInvites();

    public String generateAccessToken();

    public String generateAccessToken(String invitorName, String password);

    public void closeConnection();
}
