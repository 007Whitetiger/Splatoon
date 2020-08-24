package me.whitetiger.splatoon;

import me.whitetiger.splatoon.Commands.AddInkling;
import me.whitetiger.splatoon.Commands.GetWeapon;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Listeners.SneakingListener;
import me.whitetiger.splatoon.Listeners.WeaponListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import java.util.Objects;

public final class Splatoon extends JavaPlugin {
    private static Splatoon instance;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        this.gameManager = new GameManager();
        registerEvents();
        Objects.requireNonNull(this.getCommand("inkling")).setExecutor(new AddInkling());
        Objects.requireNonNull(this.getCommand("weapon")).setExecutor(new GetWeapon());

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

}
