package dev.advik.InviteTree;


import dev.advik.InviteTree.commands.InviteCommand;
import dev.advik.InviteTree.commands.RevokeCommand;
import dev.advik.InviteTree.commands.ViewInvitesCommand;
import dev.advik.InviteTree.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class InviteTree extends JavaPlugin {

    private Logger log;
    private Database database;

    @Override
    public void onEnable() {
        log = getLogger();
        if (!getDataFolder().exists()) {
            try {
                getDataFolder().mkdir();
            } catch (Exception e) {
                log.throwing("InviteTree", "onEnable", e);
            }
        }
        database = new Database(getDataFolder().getAbsolutePath(), log);
        getCommand("invite").setExecutor(new InviteCommand(this, database, log));
        getCommand("invites").setExecutor(new ViewInvitesCommand(this, database, log));
        getCommand("inviterevoke").setExecutor(new RevokeCommand(this, database, log));
        log.info("InviteTree has been enabled!");

    }

    @Override
    public void onDisable() {
        database.shutdown();
        log.info("InviteTree has been disabled!");
    }

}
