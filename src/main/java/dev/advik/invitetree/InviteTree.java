package dev.advik.invitetree;

import dev.advik.invitetree.listeners.LoginListner;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger logger;
    private PluginManager pluginMGR;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = getLogger();
        pluginMGR = getServer().getPluginManager();
        pluginMGR.registerEvents(new LoginListner(), this);
        logger.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Unregister the listener
        logger.info("InviteTree has been disabled!");
    }
}
