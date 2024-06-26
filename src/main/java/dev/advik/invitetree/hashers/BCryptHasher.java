package dev.advik.invitetree.hashers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BCryptHasher {

        public final static int BCRYPT_COST = 12;

        @Contract("_ -> new")
        public static @NotNull String hashPassword(@NotNull String password) {
            return BCrypt.withDefaults().hashToString(BCRYPT_COST, password.toCharArray());
        }

        public static boolean verifyPassword(@NotNull String password, String hash) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
            return result.verified;
        }
}
