package me.whitetiger.splatoon;

import me.whitetiger.splatoon.Commands.AddInkling;
import me.whitetiger.splatoon.Commands.GetWeapon;
import me.whitetiger.splatoon.Commands.Reload;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Listeners.SneakingListener;
import me.whitetiger.splatoon.Listeners.WeaponListener;
import me.whitetiger.splatoon.Utils.DevUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Splatoon extends JavaPlugin {
    private static Splatoon instance;
    private GameManager gameManager;
    private boolean dev;

    @Override
    public void onEnable() {
        instance = this;
        this.gameManager = new GameManager();
        saveDefaultConfig();

        dev = getConfig().getBoolean("debug");

        DevUtils.debug("Debug enabled!");

        registerEvents();
        Objects.requireNonNull(this.getCommand("inkling")).setExecutor(new AddInkling());
        Objects.requireNonNull(this.getCommand("weapon")).setExecutor(new GetWeapon());
        Objects.requireNonNull(getCommand("inkreload")).setExecutor(new Reload());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void registerEvents() {
        new WeaponListener(this);
        Bukkit.getPluginManager().registerEvents(new SneakingListener(), this);
        // Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
    }

    public static Splatoon getInstance() {
        return instance;
    }

    public boolean isDev() {
        return dev;
    }
}
