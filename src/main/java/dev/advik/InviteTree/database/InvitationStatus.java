package dev.advik.InviteTree.database;

public enum InvitationStatus
{
    PENDING,  // Invitee has not accepted or rejected
    ACCEPTED, // By invitee
    REJECTED, // By invitee
    CANCELLED // By inviter
}
