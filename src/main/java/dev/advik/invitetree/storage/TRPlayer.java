package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.PlayerStatus;
import dev.advik.invitetree.hashers.BCryptHasher;
import dev.advik.invitetree.impl_sqlite3.PlayerDatabase;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

// All classes are prefixed with TR to avoid conflicts with other plugins & libraries
public class TRPlayer {
    private String name;
    private String uuid;
    private TRPlayer invitedBy;
    private PlayerStatus status;
    private String accessToken;
    private ZonedDateTime invitedAt;
    private ZonedDateTime lastLogin;
    private String passwordHash;

    public TRPlayer(String name, String uuid, TRPlayer invitedBy, PlayerStatus status, String accessToken) {
        this.name = name;
        this.uuid = uuid;
        this.invitedBy = invitedBy;
        this.status = status;
        this.accessToken = accessToken;
    }

    public TRPlayer() {
        this.name = null;
        this.uuid = null;
        this.invitedBy = null;
        this.status = null;
        this.accessToken = null;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public TRPlayer getInvitedBy() {
        return invitedBy;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = BCryptHasher.hashPassword(password);
    }

    public boolean checkPassword(String password) {
        return BCryptHasher.verifyPassword(password, passwordHash);
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setInvitedAt(ZonedDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInvited() {
        return invitedBy != null;
    }

    public boolean isInvitedBy(TRPlayer player) {
        if (isInvited()) {
            return invitedBy == player;
        }
        return false;
    }

    public boolean isInvitedBy(String playerName) {
        if (isInvited()) {
            return invitedBy.getName().equals(playerName);
        }
        return false;
    }

    // Setters
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void setInvitedBy(TRPlayer invitedBy) {
        this.invitedBy = invitedBy;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void removeInvitedBy() {
        invitedBy = null;
    }

    public boolean isReady() {
        return name != null && uuid != null && status != null && accessToken != null && passwordHash != null;
    }

    public void addToDatabase(PlayerDatabase database) {
        if (!isReady()) {
            throw new IllegalStateException("Player is not ready to be added to the database");
        }
        // Add player to database
        database.executeInsert(
                "INSERT INTO players " +
                        "(" +
                        "   player_name," +
                        "   player_uuid," +
                        "   player_status," +
                        "   access_token," +
                        "   invited_by," +
                        "   invited_at," +
                        "   last_login," +
                        "   password_hash" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Map.of(
                        1, name,
                        2, uuid,
                        3, status.toString(),
                        4, accessToken,
                        5, invitedBy != null ? invitedBy.getName() : null,
                        6, invitedAt != null ? invitedAt.toString() : null,
                        7, lastLogin != null ? lastLogin.toString() : null,
                        8, passwordHash
                ));
    }

    public void updateInDatabase(PlayerDatabase database) {
        if (!isReady()) {
            throw new IllegalStateException("Player is not ready to be updated in the database");
        }
        // Update player in database
        database.executeUpdate(
                "UPDATE players SET " +
                        "player_status = ?, " +
                        "access_token = ?, " +
                        "invited_by = ?, " +
                        "invited_at = ?, " +
                        "last_login = ?, " +
                        "password_hash = ? " +
                        "WHERE player_name = ?",
                Map.of(
                        1, status.toString(),
                        2, accessToken,
                        3, invitedBy != null ? invitedBy.getName() : null,
                        4, invitedAt != null ? invitedAt.toString() : null,
                        5, lastLogin != null ? lastLogin.toString() : null,
                        6, passwordHash,
                        7, name
                ));
    }

    public void deleteFromDatabase(PlayerDatabase database) {
        if (!isReady()) {
            throw new IllegalStateException("Player is not ready to be deleted from the database");
        }
        // Delete player from database
        database.executeUpdate(
                "DELETE FROM players WHERE player_name = ?",
                Map.of(1, name));
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken.toString();
    }


    public static TRPlayer fromDatabase(PlayerDatabase database, String playerName) {
        TRPlayer player = new TRPlayer();
        String result = database.executeQuery(
                "SELECT * FROM players WHERE player_name = ?",
                Map.of(1, playerName)
        );

        return getTrPlayer_common(database, player, result);
    }

    public static TRPlayer fromDatabase(PlayerDatabase database, UUID playerUUID) {
        TRPlayer player = new TRPlayer();
        String result = database.executeQuery(
                "SELECT * FROM players WHERE player_uuid = ?",
                Map.of(1, playerUUID.toString())
        );

        return getTrPlayer_common(database, player, result);
    }

    private static TRPlayer getTrPlayer_common(PlayerDatabase database, TRPlayer player, String result) {
        if (result == null) {
            return null;
        }

        String[] data = result.split(",");
        player.setName(data[0]);
        player.setUuid(data[1]);
        player.setStatus(PlayerStatus.valueOf(data[2]));
        player.setAccessToken(UUID.fromString(data[3]));
        player.setInvitedBy(TRPlayer.fromDatabase(database, data[4]));
        player.setInvitedAt(ZonedDateTime.parse(data[5]));
        player.setLastLogin(ZonedDateTime.parse(data[6]));
        player.setPasswordHash(data[7]);
        return player;
    }
}
