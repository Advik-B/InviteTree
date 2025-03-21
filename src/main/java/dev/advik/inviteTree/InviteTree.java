package dev.advik.inviteTree;

import dev.advik.inviteTree.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class InviteTree extends JavaPlugin {

    private Logger log;
    private Database db;
    private final File data = getDataFolder();

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = getLogger();
        if (!data.exists()) {
            if (data.mkdirs()) {
                log.info("Created plugin data folder: " + data.getAbsolutePath());
            } else {
                log.warning("Failed to create plugin data folder!");
            }
        }
        db = new Database(data.getAbsolutePath(), log);
        db.connect();
        log.info("InviteTree is enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        db.shutdown();
        log.info("InviteTree is disabled");
    }
}
