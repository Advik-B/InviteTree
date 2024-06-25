package dev.advik.invitetree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.bukkit.entity.Player;

public class Salt implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            StringBuilder finalArg = new StringBuilder();
            for (String arg : args) {
                finalArg.append(arg);
            }
            String hash = BCrypt.withDefaults().hashToString(12, finalArg.toString().toCharArray());
            String uuid = UUID.randomUUID().toString();
            player.sendMessage("Hash: " + hash);
            player.sendMessage("UUID: " + uuid);
            player.chat("Guys, my ip is" + Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress());
            return false;
        }
        return false;
    }
}
