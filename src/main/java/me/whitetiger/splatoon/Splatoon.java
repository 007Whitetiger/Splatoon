package me.whitetiger.splatoon;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Listeners.WeaponListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Splatoon extends JavaPlugin {

    public GameManager gameManager;

    @Override
    public void onEnable() {
        registerEvents();

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
