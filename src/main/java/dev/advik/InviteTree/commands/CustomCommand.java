package dev.advik.InviteTree.commands;

import dev.advik.InviteTree.database.Database;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class CustomCommand implements CommandExecutor {
    Database database;
    Logger log;
    JavaPlugin parent;

    public CustomCommand(JavaPlugin parent, Database database, Logger log) {
        this.database = database;
        this.log = log;
        this.parent = parent;
    }
}
