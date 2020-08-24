package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Location;
import org.bukkit.Material;
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
    private HashMap<Player, BukkitRunnable> running = new HashMap<>();
    private HashMap<Player, List<ArmorStand>> armorStands = new HashMap<>();


    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if (event.isSneaking()) {

            ArmorStand armorStand = createArmorStand(player.getLocation().subtract(0, 0.7, 0));

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    armorStand.teleport(player.getLocation().subtract(0, 0.7, 0));
                    armorStand.getLocation().getBlock();
                }
            };
            running.put(player, runnable);
            if (!armorStands.containsKey(player)) {
                armorStands.put(player, new ArrayList<>());
            }
            armorStands.get(player).add(armorStand);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
            running.get(player).runTaskTimer(plugin, 1, 10);
        } else {
            running.get(player).cancel();
            armorStands.get(player).forEach(Entity::remove);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
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
}
