package dev.advik.invitetree;

import dev.advik.invitetree.listeners.LoginListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        PluginManager pluginMGR = getServer().getPluginManager();
        pluginMGR.registerEvents(new LoginListener(), this);
        logger.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("InviteTree has been disabled!");
    }
}
