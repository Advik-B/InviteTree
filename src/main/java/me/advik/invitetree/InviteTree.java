package me.advik.invitetree;

import org.bukkit.plugin.java.JavaPlugin;

public final class InviteTree extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("InviteTree has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("InviteTree has been disabled!");
    }
}
