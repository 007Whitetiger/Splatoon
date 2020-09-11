package me.whitetiger.splatoon.Commands;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Weapons.StartManager;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class Start implements CommandExecutor {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        new StartManager(gameManager.getTeams().get(0), gameManager.getTeams().get(1), 20);

        return true;
    }
}
