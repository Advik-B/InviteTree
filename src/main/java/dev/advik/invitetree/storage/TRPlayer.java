package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.PlayerStatus;
import dev.advik.invitetree.tree.PlayerDatabase;

import java.util.UUID;

// All classes are prefixed with TR to avoid conflicts with other plugins & libraries
public class TRPlayer {
    private String name;
    private String uuid;
    private TRPlayer invitedBy;
    private PlayerStatus status;
    private String accessToken;

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

    public void addToDatabase(PlayerDatabase database) {
        database.addPlayer(this);
    }

}
