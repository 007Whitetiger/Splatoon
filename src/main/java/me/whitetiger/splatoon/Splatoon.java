package me.whitetiger.splatoon;

import me.whitetiger.splatoon.Commands.*;
import me.whitetiger.splatoon.Game.ConfigManager;
import me.whitetiger.splatoon.Game.EndGameManager;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Listeners.SneakingListener;
import me.whitetiger.splatoon.Listeners.WeaponListener;
import me.whitetiger.splatoon.Utils.DevUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class Splatoon extends JavaPlugin {
    private static Splatoon instance;

    private GameManager gameManager;
    private EndGameManager endGameManager;

    private SneakingListener sneakingListener;

    private boolean dev;

    @Override
    public void onEnable() {

        instance = this;
        this.gameManager = new GameManager();
        endGameManager = new EndGameManager();
        new ConfigManager();
        saveDefaultConfig();

        dev = getConfig().getBoolean("debug");

        DevUtils.debug("Debug enabled!");

        registerEvents();
        registerTickHandler();
        getCommand("eval").setExecutor(new Eval());


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

    public EndGameManager getEndGameManager() {
        return endGameManager;
    }

    public void registerEvents() {
        new WeaponListener(this);
        sneakingListener = new SneakingListener();
        Bukkit.getPluginManager().registerEvents(sneakingListener, this);
        // Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
    }

    public void registerTickHandler() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : gameManager.getPlayers().keySet()) {
                    sneakingListener.wallClimbTest(player);
                }
            }
        }.runTaskTimer(this, 1, 1);
    }

    public static Splatoon getInstance() {
        return instance;
    }

    public boolean isDev() {
        return dev;
    }
}
