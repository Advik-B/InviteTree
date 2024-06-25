package dev.advik.invitetree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.UUID;
import dev.advik.invitetree.datastore.StorageManager;

public class InviteDebug implements CommandExecutor {

    private StorageManager manager;

    public InviteDebug(StorageManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /invitedebug <player>");
            return true;
        } else if (args.length == 1) {
            String playerName = args[0];
            System.out.println("Player name: " + playerName);
            manager.addPlayer(playerName, null, "password", UUID.randomUUID().toString());
            return true;
        }
        return false;
    }
}