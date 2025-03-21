package dev.advik.inviteTree;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger log;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = getLogger();
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                log.info("Created plugin data folder: " + getDataFolder().getAbsolutePath());
            } else {
                log.warning("Failed to create plugin data folder!");
            }
        }
        log.info("InviteTree is enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("InviteTree is disabled");
    }
}
