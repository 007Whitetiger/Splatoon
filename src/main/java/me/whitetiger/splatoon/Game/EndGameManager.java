package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Teams.ITeam;
import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Utils.Cube;
import me.whitetiger.splatoon.Utils.DevUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class EndGameManager {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();

    private HashMap<ITeam, Integer> teamScoreHashMap = new HashMap<>();

    private ITeam winner;

    public void endGame() {
        DevUtils.debug("Starting endGame");
        calculateWinner();
        if (winner != null) {
            for (Player player : gameManager.getPlayers().keySet()) {
                if (gameManager.getPlayer(player).getTeam() == winner) {
                    player.sendMessage("§aYOU WON");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§aWINNER"));
                } else {
                    player.sendMessage("§cYou Lost!");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§cLOSS"));
                    player.sendMessage(winner.getName() + " won!");
                }
            }
        } else {
            for (Player player : gameManager.getPlayers().keySet()) {
                player.sendMessage("§6TIE");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6TIE"));
            }
        }
    }

    private void calculateWinner() {

        List<ITeam> teams = gameManager.getTeams();

        for (ITeam team : teams) {
            teamScoreHashMap.put(team, 0);
        }

        DevUtils.debug(ConfigManager.getFirstAreaLocation().toString());

        DevUtils.debug(ConfigManager.getSecondAreaLocation().toString());
        
        Cube cube = new Cube(ConfigManager.getFirstAreaLocation(), ConfigManager.getSecondAreaLocation());

        cube.loopOverArea((block) -> {
            ITeam team = getTeam(block.getType());
            if (team != null) {
                teamScoreHashMap.put(team, teamScoreHashMap.get(team) + 1);
            }
        });
            
        ITeam currentWinner = null;

        for (ITeam team : teams) {
            if (currentWinner == null) {
                if (teamScoreHashMap.get(team) > 0) {
                    currentWinner = team;
                }
            } else {
                if (teamScoreHashMap.get(team) > teamScoreHashMap.get(currentWinner)) {
                    currentWinner = team;
                }
            }
        }

        if (currentWinner == null) {
            plugin.getLogger().severe("There was no winner.... ERROR");
            DevUtils.debug("No winner found!");
            winner = null;
            return;
        }

        winner = currentWinner;
        DevUtils.debug("Winner was " + winner.getName());

        List<Material> backUpBlocks = StartManager.getBackUpBlocks();
        if (backUpBlocks == null) plugin.getLogger().severe("End game was called before game was started!");
        cube.setMaterial(backUpBlocks);
    }

    private ITeam getTeam(Material material) {
        for (ITeam team : teamScoreHashMap.keySet()) {
            if (team.getWoolColor() == material) {
                return team;
            }
        }
        return null;
    }
}
