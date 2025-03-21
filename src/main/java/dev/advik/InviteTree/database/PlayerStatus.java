package dev.advik.InviteTree.database;

public enum PlayerStatus {
    ALLOWED,
    NOT_ALLOWED;

    public String getStatusString() {
        return switch (this) {
            case ALLOWED -> "Allowed";
            case NOT_ALLOWED -> "Not Allowed";
            default -> "Unknown";
        };
    }
}
