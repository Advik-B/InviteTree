package dev.advik.invitetree;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private final Logger logger;
    private final String url;

    public InviteTree() {
        logger = getLogger();
        url = "jdbc:sqlite:" + getDataFolder().getAbsolutePath() + "/players.db";
    }

    private void ensureFoldersExist() {
        // Make sure the data folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    @Override
    public void onEnable() {
        ensureFoldersExist();
        logger.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        logger.info("InviteTree has been disabled!");
    }
}
