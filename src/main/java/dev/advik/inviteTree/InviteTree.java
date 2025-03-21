package dev.advik.InviteTree;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class InviteTree extends JavaPlugin {

    private Logger log;

    @Override
    public void onEnable() {
        log = getLogger();
        log.info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        log.info("InviteTree has been disabled!");
    }

}
