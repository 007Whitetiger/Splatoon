package me.whitetiger.splatoon.Commands;

import me.whitetiger.splatoon.Splatoon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndGame implements CommandExecutor {

    private final Splatoon plugin = Splatoon.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getEndGameManager().endGame();
        return true;
    }
}
