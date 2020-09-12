package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Teams.ITeam;
import me.whitetiger.splatoon.Game.exceptions.StartException;
import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Utils.DevUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartManager implements Listener {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();

    private ITeam firstTeam;
    private ITeam secondTeam;

    private List<Inkling> playersTeam1 = new ArrayList<>();
    private List<Inkling> playersTeam2 = new ArrayList<>();

    private StartManager instance;

    public StartManager(ITeam firstTeam, ITeam secondTeam, double seconds) {
        try {
            DevUtils.debug("Started start of the game!");
            this.firstTeam = firstTeam;
            this.secondTeam = secondTeam;
            this.instance = this;

            DevUtils.debug("Teleporting " + this);

            teleport();
            // testTeam();
            if (false) {
                throw new StartException();
            }
            DevUtils.debug("SendingMessages " + this);
            Bukkit.getPluginManager().registerEvents(this, plugin);
            sendMessages();
            new BukkitRunnable() {
                @Override
                public void run() {
                    DevUtils.debug("Starting endGame timer! " + this);
                    playersTeam1.forEach(inkling -> {
                        inkling.setWaiting(false);
                    });
                    playersTeam2.forEach(inkling -> inkling.setWaiting(false));
                    unregisterWaitingEvents();
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
                player.setWaiting(true);
            } else if (player.getTeam() == secondTeam){
                player.getBukkitPlayer().teleport(ConfigManager.getSecondSpawnLocation());
                playersTeam2.add(player);
                player.setWaiting(true);
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

    private void unregisterWaitingEvents() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        PlayerToggleSneakEvent.getHandlerList().unregister(this);
    }

    @EventHandler()
    public void onWalkInkling(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.isWaiting()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInklingInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.isWaiting()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInklingSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.isWaiting()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        System.out.println("k");
        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.getTeam() == firstTeam) {
            event.setRespawnLocation(ConfigManager.getFirstSpawnLocation());
        } else if (inkling.getTeam() == secondTeam) {
            event.setRespawnLocation(ConfigManager.getSecondSpawnLocation());
        }
    }
}
