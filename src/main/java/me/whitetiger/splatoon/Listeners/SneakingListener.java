package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SneakingListener implements Listener {

    private Splatoon plugin = Splatoon.getInstance();
    private GameManager gameManager = plugin.getGameManager();


    private HashMap<Player, BukkitRunnable> running = new HashMap<>();
    private HashMap<Player, List<ArmorStand>> armorStands = new HashMap<>();


    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (event.isSneaking()) {

            ArmorStand armorStand = createArmorStand(player.getLocation().subtract(0, 0.7, 0));

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 50, 1);

            BukkitRunnable runnable = new BukkitRunnable() {
                int loop = 40;
                @Override
                public void run() {
                    Material under = player.getLocation().subtract(0, 1, 0).getBlock().getType();
                    if (under != inkling.getWoolMaterial() && under != Material.AIR) {
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
                    if (loop == 20 || loop == 39) {
                        inkling.refillInkIncrement(10);
                        player.sendMessage("§a§lReloading!");
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
            running.get(player).runTaskTimer(plugin, 1, 1);
        } else {
            stop(player);
        }
    }

    private ArmorStand createArmorStand(Location location) {
        ArmorStand armorStand = location.getWorld().spawn(location.subtract(0, 0.7, 0), ArmorStand.class);
        // armorStand.setVisible(false);
        armorStand.setVisible(false);
        armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.BLUE_WOOL));
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);

        return armorStand;
    }

    private void stop(Player player) {
        running.get(player).cancel();
        armorStands.get(player).forEach(Entity::remove);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setWalkSpeed(0.2f);
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 50, 1);
        player.stopSound(Sound.BLOCK_ANVIL_USE);
    }
}
