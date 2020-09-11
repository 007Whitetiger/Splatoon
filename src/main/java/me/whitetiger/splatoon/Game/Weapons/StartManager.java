package me.whitetiger.splatoon.Game.Weapons;

import me.whitetiger.splatoon.Game.ConfigManager;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Game.Teams.ITeam;
import me.whitetiger.splatoon.Game.exceptions.StartException;
import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Utils.DevUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartManager {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();

    private ITeam firstTeam;
    private ITeam secondTeam;

    private List<Inkling> playersTeam1 = new ArrayList<>();
    private List<Inkling> playersTeam2 = new ArrayList<>();

    public StartManager(ITeam firstTeam, ITeam secondTeam, double seconds) {
        try {
            DevUtils.debug("Started start of the game!");
            this.firstTeam = firstTeam;
            this.secondTeam = secondTeam;
            DevUtils.debug("Teleporting " + this);
            teleport();
            // testTeam();
            if (false) {
                throw new StartException();
            }
            DevUtils.debug("SendingMessages " + this);
            sendMessages();
            new BukkitRunnable() {
                @Override
                public void run() {
                    DevUtils.debug("Starting endGame timer! " + this);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            plugin.getEndGameManager().endGame();
                        }
                    }.runTaskLater(plugin, (long) (seconds * 20));
                }
            }.runTaskLater(plugin, 20 * 10);

        } catch (StartException e) {
            DevUtils.debug(Arrays.toString(e.getStackTrace()));
            plugin.getLogger().severe("Something went wrong with the start!");
            plugin.getLogger().severe(e.toString());
        }
    }

    private void teleport() {
        for (Inkling player : gameManager.getPlayers().values()) {
            if (player.getTeam() == firstTeam) {
                player.getBukkitPlayer().teleport(ConfigManager.getFirstSpawnLocation());
                playersTeam1.add(player);
            } else if (player.getTeam() == secondTeam){
                player.getBukkitPlayer().teleport(ConfigManager.getSecondSpawnLocation());
                playersTeam2.add(player);
            } else {
                DevUtils.debug(player.toString() + " " + player.getBukkitPlayer().toString() + " had no team in this game!");
                plugin.getLogger().warning("ERROR Player has no team in this game!");
                player.getBukkitPlayer().sendMessage("§cThe game started without you because you had no team!");
            }
        }
    }

    private void testTeam() throws StartException {
        if (playersTeam1.size() < 1 || playersTeam2.size() < 1) {
            throw new StartException("A team was not big enough!");
        }
    }

    private void sendMessages() {
        playersTeam1.forEach(inkling -> {
            inkling.getBukkitPlayer().sendMessage("§aThe game will start in 10 seconds!");
        });
        playersTeam2.forEach(inkling -> {
            inkling.getBukkitPlayer().sendMessage("§aThe game will start in 10 seconds!");
        });

        new BukkitRunnable() {
            int seconds = 10;
            @Override
            public void run() {
                playersTeam1.forEach(inkling -> {
                    inkling.getBukkitPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + seconds));
                });
                playersTeam2.forEach(inkling -> {
                    inkling.getBukkitPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + seconds));
                });
                seconds--;
                if (seconds <= 0) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
