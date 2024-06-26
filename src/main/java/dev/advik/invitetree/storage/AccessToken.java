package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.AccessTokenStatus;

public class AccessToken {
    String token;
    AccessTokenStatus status;

    public AccessToken(String token, AccessTokenStatus status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public AccessTokenStatus getStatus() {
        return status;
    }

    public void setStatus(AccessTokenStatus status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
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

}
