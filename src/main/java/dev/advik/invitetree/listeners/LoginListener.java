package dev.advik.invitetree.listeners;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;
import dev.advik.invitetree.datastore.StorageManager;


public class LoginListener implements Listener {

    private final StorageManager manager;

    public LoginListener(StorageManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TextComponent textComponent = text()
                .content("You're a ")
                .color(color(0x8FFAD))
                .append(text().content("Bunny").color(NamedTextColor.LIGHT_PURPLE))
                .append(text("! Press "))
                .append(
                        Component.keybind()
                                .keybind("key.jump")
                                .color(NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.BOLD, true)
                                .build()
                )
                .append(text(" to jump!"))
                .build();

        player.sendMessage(textComponent);
    }

    @EventHandler
    public void onPlayerLeave(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("You have left the server!");
    }

}