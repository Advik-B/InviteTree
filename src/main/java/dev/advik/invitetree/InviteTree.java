package dev.advik.invitetree;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        logger.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("InviteTree has been disabled!");
    }
}
