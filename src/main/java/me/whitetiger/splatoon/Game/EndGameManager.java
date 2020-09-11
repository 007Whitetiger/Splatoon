package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Teams.ITeam;
import me.whitetiger.splatoon.Splatoon;
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

public class EndGameManager {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();

    private HashMap<ITeam, Integer> teamScoreHashMap = new HashMap<>();

    private ITeam winner;

    private boolean x = true;
    private boolean y = true;
    private boolean z = true;

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
        if (ConfigManager.getXSecond() - ConfigManager.getXFirst() < 0) {
            x = false;
        }
        if (ConfigManager.getYSecond() - ConfigManager.getYFirst() < 0) {
            y = false;
        }
        if (ConfigManager.getZSecond() - ConfigManager.getZFirst() < 0) {
            z = false;
        }

        World world = ConfigManager.getWorld();
        List<ITeam> teams = gameManager.getTeams();

        for (ITeam team : teams) {
            teamScoreHashMap.put(team, 0);
        }

        DevUtils.debug(String.valueOf(ConfigManager.getXFirst()));
        DevUtils.debug(String.valueOf(ConfigManager.getYFirst()));
        DevUtils.debug(String.valueOf(ConfigManager.getZFirst()));

        DevUtils.debug(String.valueOf(ConfigManager.getXSecond()));
        DevUtils.debug(String.valueOf(ConfigManager.getYSecond()));
        DevUtils.debug(String.valueOf(ConfigManager.getZSecond()));

        Block oldBlock = null;
        Material oldMaterial = null;

        for (int x=ConfigManager.getXFirst(); getXBool(x); x = getXMinus(x)) {
            for (int y=ConfigManager.getYFirst(); getYBool(y); y = getYMinus(y)) {
                for (int z= ConfigManager.getZFirst(); getZBool(z); z = getZMinus(z)) {
                    if (oldBlock != null) {
                        oldBlock.setType(oldMaterial);
                    }
                    Block block = new Location(world, x, y, z).getBlock();
                    if (plugin.isDev()) {
                        oldMaterial = block.getType();
                        oldBlock = block;
                    }
                    ITeam team = getTeam(block.getType());
                    if (team != null) {
                        teamScoreHashMap.put(team, teamScoreHashMap.get(team) + 1);
                        DevUtils.debug("§cFOUND at " + block.getLocation().toString());
                    }
                }
            }
        }
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
    }

    private int getXMinus(int x) {
        if (this.x) return x + 1;
        else return x - 1;
    }
    private int getYMinus(int y) {
        if (this.y) return y + 1;
        else return y - 1;
    }
    private int getZMinus(int z) {
        if (this.z) return z + 1;
        else return z - 1;
    }

    private boolean getXBool(int x) {
        if (this.x) return x<= ConfigManager.getXSecond();
        else return x>= ConfigManager.getXSecond();
    }
    private boolean getYBool(int y) {
        if (this.y) return y<= ConfigManager.getYSecond();
        else return y>= ConfigManager.getYSecond();
    }
    private boolean getZBool(int z) {
        if (this.z) return z<= ConfigManager.getZSecond();
        else return z>= ConfigManager.getZSecond();
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
