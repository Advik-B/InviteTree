package dev.advik.InviteTree.database;

public enum InvitationStatus {
    PENDING,  // Invitee has not accepted or rejected
    ACCEPTED, // By invitee
    REJECTED, // By invitee
    CANCELLED; // By inviter


    public String getStatusString() {
        return switch (this) {
            case PENDING -> "Pending";
            case ACCEPTED -> "Accepted";
            case REJECTED -> "Rejected";
            case CANCELLED -> "Cancelled";
            default -> "Unknown";
        };
    }

    public Integer getColor() {
        return switch (this) {
            case PENDING -> 0x00FFFF;
            case ACCEPTED -> 0x00FF00;
            case REJECTED -> 0xFF0000;
            case CANCELLED -> 0xFFA500;
            default -> 0xFFFFFF;
        };
    }
    }