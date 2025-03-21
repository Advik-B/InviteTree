package dev.advik.InviteTree.commands;

import dev.advik.InviteTree.database.Database;
import dev.advik.InviteTree.database.InvitationStatus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class InviteCommand implements CommandExecutor {

    Database database;
    Logger log;
    JavaPlugin parent;

    public InviteCommand(JavaPlugin parent, Database database, Logger log) {
        this.database = database;
        this.log = log;
        this.parent = parent;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            return false;
        }

        Connection conn = database.getConn();
        if (conn == null) {
            log.severe("Could not connect to database");
            sender.sendMessage(Component.text("Could not connect to database", NamedTextColor.RED));
            return false;
        }

        String playerToInvite = args[0];
        // Check if the player is already invited
        try {
            var statement = conn.prepareStatement("SELECT * FROM users WHERE name = '" + playerToInvite + "';");
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sender.sendMessage(Component.text("Player is already invited", NamedTextColor.BLUE));
                return true;
            }
        } catch (SQLException e) {
            log.throwing("InviteTree", "InviteCommand", e);
        }

        // First, check if the player is allowed to be invited
        // Check if player is banned
        parent.getServer().getBannedPlayers().forEach(player -> {
            if (Objects.equals(player.getName(), playerToInvite)) {
                sender.sendMessage(Component.text(playerToInvite+" is banned. You cannot invite a banned player!", NamedTextColor.RED));
            }
        });

        // Check if player is already invited (by the inviter)
        try {
            var statement = conn.prepareStatement("SELECT * FROM invites WHERE inviter = '" + sender.getName() + "' AND invitee = '" + playerToInvite + "';");
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sender.sendMessage(Component.text("You have already invited "+playerToInvite, NamedTextColor.BLUE));
                return true;
            }
        } catch (SQLException e) {
            log.throwing("InviteTree", "InviteCommand", e);
        }

        var uuid = UUID.randomUUID().toString();
        try {
            var statement = conn.prepareStatement("INSERT INTO invites (uuid, inviter, invitee, status, invited_at, accepted_at) VALUES (?, ?, ?, ?, ?, ?);");
            statement.setString(1, uuid);
            statement.setString(2, sender.getName());
            statement.setString(3, playerToInvite);
            statement.setInt(4, InvitationStatus.PENDING.ordinal());
            statement.setLong(5, System.currentTimeMillis());
            statement.setLong(6, 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.throwing("InviteTree", "InviteCommand", e);
        }

        sender.sendMessage(Component.text("Invitation added", TextColor.color(0x6400FF)));
        Component message = Component.text("Invite Code: ", TextColor.color(0x00FF00))
                .append(Component.text(uuid)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                        .clickEvent(ClickEvent.copyToClipboard(uuid)));
        sender.sendMessage(message);

        return true;
    }
}