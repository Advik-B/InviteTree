package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.AccessTokenStatus;
import dev.advik.invitetree.tree.PlayerDatabase;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public class TRAccessToken {
    UUID token;
    AccessTokenStatus status;
    ZonedDateTime invitedAt;
    ZonedDateTime usedAt;

    public TRAccessToken(UUID token, AccessTokenStatus status) {
        this.token = token;
        this.status = status;
    }

    public UUID getToken() {
        return token;
    }

    public AccessTokenStatus getStatus() {
        return status;
    }

    public void setStatus(AccessTokenStatus status) {
        this.status = status;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setUsed() {
        status = AccessTokenStatus.USED;
    }

    public void setDisabled() {
        status = AccessTokenStatus.DISABLED;
    }

    public void setExpired() {
        status = AccessTokenStatus.EXPIRED;
    }

    public void setInvalid() {
        status = AccessTokenStatus.INVALID;
    }

    public boolean isValid() {
        return status == AccessTokenStatus.VALID;
    }

    public boolean isUsed() {
        return status == AccessTokenStatus.USED;
    }

    public boolean isUsedOrInvalid() {
        return status == AccessTokenStatus.USED || status == AccessTokenStatus.INVALID;
    }

    public boolean isDisabled() {
        return status == AccessTokenStatus.DISABLED;
    }

    public boolean isExpired() {
        return status == AccessTokenStatus.EXPIRED;
    }

    public static TRAccessToken newToken() {
        return new TRAccessToken(UUID.randomUUID(), AccessTokenStatus.VALID);
    }

    public static TRAccessToken newToken(AccessTokenStatus status) {
        return new TRAccessToken(UUID.randomUUID(), status);
    }

    public void addToDatabase(PlayerDatabase database) {
        database.executeInsert(
                "INSERT INTO access_tokens " +
                        "(" +
                        "   token," +
                        "   status," +
                        "   invited_at" +
                        "   used_at" +
                        ") " +
                        "VALUES " +
                        "( ?, ?, ?, ? )",
                Map.of(
                        1, token.toString(),
                        2, status.toString(),
                        3, invitedAt.toString(),
                        4, usedAt.toString()
                )
        );
    }

    public void updateInDatabase(PlayerDatabase database) {
        database.executeUpdate(
                "UPDATE access_tokens SET " +
                        "status = ?, " +
                        "invited_at = ?, " +
                        "used_at = ? " +
                        "WHERE token = ?",
                Map.of(
                        1, status.toString(),
                        2, invitedAt.toString(),
                        3, usedAt.toString(),
                        4, token.toString()
                )
        );
    }

    public void deleteFromDatabase(PlayerDatabase database) {
        database.executeUpdate(
                "DELETE FROM access_tokens WHERE token = ?",
                Map.of(
                        1, token.toString()
                )
        );
    }

    public void setInvitedAt(ZonedDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    public void setUsedAt(ZonedDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    public ZonedDateTime getUsedAt() {
        return usedAt;
    }

    public void setInvited() {
        status = AccessTokenStatus.VALID;
    }

    public void setInvited(ZonedDateTime invitedAt) {
        status = AccessTokenStatus.VALID;
        this.invitedAt = invitedAt;
    }

    public void setUsed(ZonedDateTime usedAt) {
        status = AccessTokenStatus.USED;
        this.usedAt = usedAt;
    }

    public static TRAccessToken fromDatabase(PlayerDatabase database, UUID token) {
        String result = database.executeQuery("SELECT * FROM access_tokens WHERE token = " + token.toString() + ";");
        if (result == null) {
            return null;
        }
        String[] data = result.split(",");
        TRAccessToken accessToken = new TRAccessToken(UUID.fromString(data[0]), AccessTokenStatus.valueOf(data[1]));
        accessToken.setInvitedAt(ZonedDateTime.parse(data[2]));
        accessToken.setUsedAt(ZonedDateTime.parse(data[3]));
        return accessToken;

    }

}