package dev.advik.InviteTree.commands;

import dev.advik.InviteTree.database.Database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Logger;

public class RevokeCommand extends CustomCommand {

    public RevokeCommand(JavaPlugin parent, Database database, Logger log) {
        super(parent, database, log);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length != 1) {
            return false;
        }

        String target = args[0];
        // Check if the target is a valid player name or UUID
        UUID.fromString(target);    
    }
}