package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.AccessTokenStatus;
import dev.advik.invitetree.tree.PlayerDatabase;

import java.time.ZonedDateTime;
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

    public void addToDatabase(PlayerDatabase database) {}

}