package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Splatoon;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SneakingListener implements Listener {

    private final Splatoon plugin = Splatoon.getInstance();
    private final GameManager gameManager = plugin.getGameManager();


    private final HashMap<Player, BukkitRunnable> running = new HashMap<>();
    private final HashMap<Player, List<ArmorStand>> armorStands = new HashMap<>();


    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();
        Inkling inkling = gameManager.getPlayer(player);

        if (event.isSneaking()) {

            Material under = getMaterialUnderFeet(player);

            if (under != inkling.getWoolMaterial()) return;

            ArmorStand armorStand = createArmorStand(player.getLocation().subtract(0, 0.7, 0), inkling);

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 50, 1);

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


}
