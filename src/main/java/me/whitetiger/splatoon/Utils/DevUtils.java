package me.whitetiger.splatoon.Utils;

import me.whitetiger.splatoon.Splatoon;

public class DevUtils {

    private static Splatoon plugin = Splatoon.getInstance();

    public static void debug(String string) {
        if (plugin.isDev()) {
            System.out.println(string);
        }
    }
}
