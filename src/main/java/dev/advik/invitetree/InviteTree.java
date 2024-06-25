package dev.advik.invitetree;

import dev.advik.invitetree.commands.Salt;
import dev.advik.invitetree.datastore.StorageManager;
import dev.advik.invitetree.listeners.LoginListener;
import dev.advik.invitetree.commands.InviteDebug;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger logger;
    private final String url;
    private StorageManager storageManager;

    public InviteTree() {
        logger = getLogger();
        url = "jdbc:sqlite:" + getDataFolder().getAbsolutePath() + "/players.db";
    }

    @Override
    public void onEnable() {
        // Make sure the data folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        storageManager = new StorageManager(url, logger);
        storageManager.connect();
        storageManager.createTables();
        PluginManager pluginMGR = getServer().getPluginManager();
        pluginMGR.registerEvents(new LoginListener(storageManager), this);
        getCommand("inviteDebug").setExecutor(new InviteDebug(storageManager));
        getCommand("salt").setExecutor(new Salt());
        logger.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        try {
            storageManager.disconnect();
        } catch (Exception e) {
            logger.warning("Failed to disconnect from the database!");
        }
        // Plugin shutdown logic
        logger.info("InviteTree has been disabled!");
    }
}
