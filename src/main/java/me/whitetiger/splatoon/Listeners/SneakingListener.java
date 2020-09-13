package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Splatoon;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SneakingListener implements Listener {

    private final Splatoon plugin = Splatoon.getInstance();
    private final GameManager gameManager = plugin.getGameManager();


    private final HashMap<Player, BukkitRunnable> running = new HashMap<>();
    private final HashMap<Player, List<ArmorStand>> armorStands = new HashMap<>();

    private final Set<Player> climbing = new HashSet<>();

    private final BlockFace[] blockFaces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST};


    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {


        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.isWaiting()) return;

        if (event.isSneaking()) {

            Material under = getMaterialUnderFeet(player);

            if (under != inkling.getWoolMaterial()) return;

            ArmorStand armorStand = createArmorStand(player.getLocation().subtract(0, 0.7, 0), inkling);

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 50, 1);

            player.spawnParticle(Particle.SQUID_INK, player.getLocation(), 30);

            BukkitRunnable runnable = new BukkitRunnable() {
                int loop = 40;
                @Override
                public void run() {

                    Material under = getMaterialUnderFeet(player);

                    if (under != inkling.getWoolMaterial()) {
                        player.setSneaking(false);
                        stop(player);
                        return;
                    }


                    armorStand.teleport(player.getLocation().subtract(0, 0.7, 0));
                    armorStand.getLocation().getBlock();



                    if (loop >= 40) {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 50, 1);
                        loop = 0;
                    }
                    if ((loop == 20 || loop == 39) && !inkling.inkFull()) {
                        inkling.refillInkIncrement(10);
                        if (inkling.inkFull()) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§lRELOADED"));
                        } else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + inkling.getInkPercentage()));
                        }
                    }

                    loop++;
                }
            };
            running.put(player, runnable);
            if (!armorStands.containsKey(player)) {
                armorStands.put(player, new ArrayList<>());
            }
            armorStands.get(player).add(armorStand);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
            player.setWalkSpeed(0.5f);
            runnable.runTaskTimer(plugin, 1, 1);
        } else {
            stop(player);
        }
    }

    private ArmorStand createArmorStand(Location location, Inkling inkling) {
        ArmorStand armorStand = location.getWorld().spawn(location.subtract(0, 0.7, 0), ArmorStand.class);
        // armorStand.setVisible(false);
        armorStand.setVisible(false);
        armorStand.getEquipment().setItemInMainHand(new ItemStack(inkling.getWoolMaterial()));
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);

        armorStand.setMetadata("SplatoonArmorStand", new FixedMetadataValue(plugin, "YES"));

        return armorStand;
    }

    private void stop(Player player) {
        if (!running.containsKey(player)) return;
        running.get(player).cancel();
        armorStands.get(player).forEach(Entity::remove);

        running.remove(player);
        armorStands.remove(player);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setWalkSpeed(0.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 50, 1);
        player.stopSound(Sound.BLOCK_ANVIL_USE);
    }

    private Material getMaterialUnderFeet(Player player) {
        int down = 1;
        Material under = player.getLocation().subtract(0, down, 0).getBlock().getType();

        while (under == Material.AIR) {
            down++;
            under = player.getLocation().subtract(0, down, 0).getBlock().getType();
        }
        return under;
    }

    public void wallClimbTest(Player player) {

        Inkling inkling = gameManager.getPlayer(player);

        if (climbing.contains(player)) return;
        if (!player.isSneaking()) return;

        Location loc = player.getLocation();

        Block blockPlayer = loc.getBlock();

        Block blockNearby = null;
        BlockFace blockFace = null;

        for (BlockFace face : blockFaces) {
            Block temporary = blockPlayer.getRelative(face);
            if (temporary.getType() != Material.AIR && inkling.getWoolMaterial() == temporary.getType()) {
                blockNearby = temporary;
                blockFace = face;
            }
        }

        if (blockNearby == null) return;

        climbing.add(player);

        climb(loc, player, blockFace, inkling);

    }

    private void climb(Location loc, Player player, BlockFace blockFace, Inkling inkling) {

        if (normalCheck(loc, blockFace, player)) {
            player.setGravity(false);
            player.teleport(loc.add(0, 1, 0));
            new BukkitRunnable() {

                @Override
                public void run() {

                    Location playerLoc = player.getLocation();
                    Material playerBlockType = playerLoc.getBlock().getType();

                    if (normalCheck(playerLoc, blockFace, player) && playerBlockType == inkling.getWoolMaterial()) {
                        player.teleport(playerLoc.add(0, 0.2, 0));
                    } else if (normalCheck(playerLoc, blockFace, player) && playerBlockType == Material.AIR && checkXZ(loc, playerLoc)) {
                        player.setGravity(false);
                    } else {
                        player.setGravity(true);
                        climbing.remove(player);
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 2, 2);
        } else {
            climbing.remove(player);
        }
    }

    private boolean normalCheck(Location location, BlockFace blockFace, Player player) {
        switch (blockFace) {
            case WEST:
                return location.getX() - (int)location.getX() <= 0.30000001192093 && running.containsKey(player);
            case NORTH:
                return location.getZ() - (int) location.getZ() <= 0.30000001192093 && running.containsKey(player);
            case EAST:
                return location.getX() - (int) location.getX() >= 0.69999998807907 && running.containsKey(player);
            case SOUTH:
                return location.getZ() - (int)location.getZ() >= 0.69999998807907 && running.containsKey(player);
            default:
                return false;
        }
    }

    private boolean checkXZ(Location oldLocation, Location newLocation) {
        Location newLocationBlock = newLocation.getBlock().getLocation().clone();

        return (oldLocation.getX() == newLocationBlock.getX()) && (oldLocation.getZ() == newLocationBlock.getZ());
    }

}
