package me.whitetiger.splatoon.Utils;

import java.util.logging.Level;

import me.whitetiger.splatoon.Splatoon;

public class DevUtils {

    private static final Splatoon plugin = Splatoon.getInstance();

    public static void debug(String string) {
        if (plugin.isDev()) {
            plugin.getLogger().log(Level.INFO, string);
        }
    }
}
