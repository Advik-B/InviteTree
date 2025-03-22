package dev.advik.InviteTree.commands;

import dev.advik.InviteTree.database.Database;
import dev.advik.InviteTree.database.InvitationStatus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ViewInvitesCommand extends CustomCommand {

    public ViewInvitesCommand(JavaPlugin parent, Database database, Logger log) {
        super(parent, database, log);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Connection conn = database.getConn();
        if (conn == null) {
            log.severe("Could not connect to database");
            sender.sendMessage(Component.text("Could not connect to database", NamedTextColor.RED));
            return false;
        }

        String player = sender.getName();
        String target = (args.length == 1 && sender.hasPermission("invitetree.invites.all")) ? args[0] : player;

        if (args.length == 1 && !sender.hasPermission("invitetree.invites.all")) {
            sender.sendMessage(Component.text("You do not have permission to view other players' invites", NamedTextColor.RED));
            return true;
        }

        try (PreparedStatement statement = conn.prepareStatement("SELECT invitee, uuid, status FROM invites WHERE inviter = ?")) {
            statement.setString(1, target);
            return displayInvites(sender, statement, target);
        } catch (SQLException e) {
            log.severe("Error retrieving invites: " + e.getMessage());
            e.printStackTrace();
            sender.sendMessage(Component.text("An error occurred while retrieving invites.", NamedTextColor.RED));
            return false;
        }
    }

    private boolean displayInvites(@NotNull CommandSender sender, PreparedStatement statement, String target) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            Component message = Component.text("《========== " + (target.equals(sender.getName()) ? "Your Invites" : "Invites for " + target) + " ==========》", NamedTextColor.BLUE);
            int invites = 0;

            while (resultSet.next()) {
                invites++;
                String invitee = resultSet.getString("invitee");
                String uuid = resultSet.getString("uuid");
                InvitationStatus status = InvitationStatus.values()[resultSet.getInt("status")];
                String uuidText = (status == InvitationStatus.PENDING) ? "[Click to copy invite code]" : uuid;

                Component inviteMessage = Component.text("\n➤ Player: ", NamedTextColor.GOLD)
                        .append(Component.text(invitee, NamedTextColor.YELLOW))
                        .append(Component.text("\n    Invite Code: ", NamedTextColor.WHITE)
                                .append(Component.text(uuidText, TextColor.color(0x00FF00))
                                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                                        .clickEvent(ClickEvent.copyToClipboard(uuid))))
                        .append(Component.text("\n    Status: ", NamedTextColor.WHITE))
                        .append(Component.text(status.getStatusString(), TextColor.color(status.getColor())))
                        .append(Component.text("\n--------------------------------", NamedTextColor.GRAY));

                message = message.append(inviteMessage);
            }

            if (invites == 0) {
                sender.sendMessage(Component.text("No invites found.", NamedTextColor.GRAY));
            } else {
                sender.sendMessage(message);
            }
            return true;
        }
    }
}
