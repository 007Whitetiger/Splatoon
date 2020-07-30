package me.whitetiger.splatoon;

import me.whitetiger.splatoon.Commands.AddInkling;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Listeners.WeaponListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Splatoon extends JavaPlugin {
    public static Splatoon instance;
    public GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        this.gameManager = new GameManager();
        registerEvents();
        Objects.requireNonNull(this.getCommand("inkling")).setExecutor(new AddInkling());

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
    }
}
