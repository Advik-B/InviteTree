package dev.advik.invitetree.storage;

import dev.advik.invitetree.database.PlayerStatus;

// All classes are prefixed with TR to avoid conflicts with other plugins & libraries
public class TRPlayer {
    private final String name;
    private final String uuid;
    private final TRPlayer invitedBy;
    private final PlayerStatus status;
    private final String accessToken;
}
