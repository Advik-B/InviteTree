package dev.advik.invitetree.tree;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import dev.advik.invitetree.impl_sqlite3.PlayerDatabase;
import dev.advik.invitetree.storage.TRAccessToken;
import dev.advik.invitetree.storage.TRPlayer;
import dev.advik.invitetree.exceptions.PasswordMismatch;
import net.kyori.adventure.text.Component;

public class LoginCommand {

    PlayerDatabase pDB;

    LoginCommand(PlayerDatabase pDB) {
        this.pDB = pDB;
    }

    public boolean login(Player player, String password) throws PasswordMismatch {
        if (player == null) {
            return false;
        }
        TRPlayer trPlayer = TRPlayer.fromDatabase(pDB, player.getName());
        if (trPlayer == null) {
            player.sendMessage("You are not registered, please register first!");
            return false;
        }

        if (trPlayer.checkPassword(password)) {
            return true;
        } else {
            throw new PasswordMismatch("Password mismatch");
        }
    }
}
