package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;

import java.util.Objects;

public class ConfigManager {

    private static Splatoon plugin;
    private static Configuration config;

    public ConfigManager() {
        plugin = Splatoon.getInstance();
        config = plugin.getConfig();
    }

    public static World getWorld() {
        return Bukkit.getWorld(Objects.requireNonNull(config.getString("world")));
    }

    public static int getXFirst() {
        return config.getInt("firstX");
    }
    public static int getYFirst() {
        return config.getInt("firstY");
    }
    public static int getZFirst() {
        return config.getInt("firstZ");
    }

    public static int getXSecond() {
        return config.getInt("secondX");
    }
    public static int getYSecond() {
        return config.getInt("secondY");
    }
    public static int getZSecond() {
        return config.getInt("secondZ");
    }


    public static Location getFirstSpawnLocation() {
        return new Location(Bukkit.getWorld(Objects.requireNonNull(config.getString("world"))), config.getInt("firstLocationX"), config.getInt("firstLocationY"), config.getInt("firstLocationZ"));
    }

    public static Location getSecondSpawnLocation() {
        return new Location(Bukkit.getWorld(Objects.requireNonNull(config.getString("world"))), config.getInt("secondLocationX"), config.getInt("secondLocationY"), config.getInt("secondLocationZ"));
    }
}
