/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Utils.Cooldowns;
import me.whitetiger.splatoon.Utils.DevUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class WeaponListener implements Listener {
    private final Splatoon plugin;
    private final GameManager gameManager;
    private final List<Material> transparent = new ArrayList<>(Arrays.asList(Material.AIR, Material.GRASS, Material.BARRIER, Material.BEACON, Material.TALL_GRASS));

    private final List<Player> reloading = new ArrayList<>();

    public WeaponListener(Splatoon plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFireEvent(PlayerInteractEvent e) {
        if (e.getAction().toString().toLowerCase().contains("left")) return;


        if (!(e.getMaterial() == Material.STICK)) return;
        Player p = e.getPlayer();

        if (p.isSneaking()) return;

        Inkling inkling = gameManager.getPlayer(p);

        if (inkling == null) return;

        if (inkling.isWaiting()) return;

        if (inkling.getInk() < 10) {
            p.sendMessage("§cYou're out of ink!");
            return;
        }

        Weapon weapon = inkling.getWeapon();

        if (Cooldowns.isCooldowned(weapon)) {
            p.sendMessage("§cThis weapon is on cooldown!");
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c" + Cooldowns.getCooldown(weapon) + "S"));
            return;
        }

        weapon.addCooldown();

        switch (weapon.getWeaponType()) {
            case GUN:
                useWeapon(p, inkling, weapon);
                break;
            case GRENADE:
                useGrenade(p, inkling, weapon);
                break;
        }

    }

    private void useWeapon(Player p, Inkling inkling, Weapon weapon) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 35, 1);

        inkling.useWeapon();
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6" + inkling.getInkPercentage()));

        if (reloading.contains(p)) {
            reloading.remove(p);
            p.sendMessage("§cReloading stopped!");
        }

        List<Entity> entities = p.getNearbyEntities(40, 40, 40);
        BlockIterator blocks = new BlockIterator(p, weapon.getRange());
        Block block;
        while (blocks.hasNext()) {

            block = blocks.next();
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity)) continue;
                if (!p.hasLineOfSight(entity)) continue;

                LivingEntity living = (LivingEntity) entity;

                if (living.isDead()) continue;

                double entityX = entity.getLocation().getX();
                double entityY = entity.getLocation().getY();
                double entityZ = entity.getLocation().getZ();

                if ((blockX <= entityX && entityX <= blockX +1) && (blockZ <= entityZ && entityZ <= blockZ+1) && (blockY -1 <= entityY && entityY <= blockY +.75)) {// all values here are for the hitbox
                    Location vector = p.getLocation().subtract(entity.getLocation());

                    DevUtils.debug(String.valueOf(p.getLocation().distance(living.getLocation())));
                    if (p.getLocation().distance(living.getLocation()) > weapon.getRange()) continue;


                    p.sendMessage(String.valueOf(weapon.getDamage()));
                    Objects.requireNonNull(living.getLocation().getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, living.getEyeLocation(),10);

                    if (living instanceof Player) {
                        Inkling enemy = gameManager.getPlayer((Player) living);
                        if (enemy.getTeam() == inkling.getTeam()) return;
                    }
                    living.damage(weapon.getDamage());

                    return;
                }
            }
        }

        Block target = p.getTargetBlockExact(weapon.getRange());

        if (target == null) return;

        if (!transparent.contains(target.getType())) {
            target.setType(inkling.getWoolMaterial());
        }

        int radius = weapon.getSplash();

        for (int x = radius; x >= -radius; x--) {
            for (int y = radius; y >= -radius; y--) {
                for (int z = radius; z >= -radius; z--) {
                    Block locBlock = target.getRelative(x, y, z);
                    if (!transparent.contains(locBlock.getType())) {
                        locBlock.setType(inkling.getWoolMaterial());
                    }
                }
            }
        }
        weapon.doCustomBehavior();
    }

    private void useGrenade(Player p, Inkling inkling, Weapon weapon) {


        ArmorStand armorStand = Objects.requireNonNull(p.getLocation().getWorld()).spawn(p.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setArms(true);
        armorStand.setMetadata("SplatoonArmorStand", new FixedMetadataValue(plugin, "yes"));
        armorStand.getEquipment().setItemInMainHand(new ItemStack(inkling.getWoolMaterial()));
        armorStand.setVelocity(p.getEyeLocation().getDirection().multiply(2));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (armorStand.isOnGround()) {
                    Location armorStandLocation = armorStand.getLocation().clone();

                    Block target = armorStandLocation.getBlock();

                    int radius = weapon.getSplash();

                    armorStand.getNearbyEntities(2, 2, 2).forEach(entity -> {
                        if (entity == p) return;
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).damage(weapon.getDamage());
                        }
                    });


                    for (int x = radius; x >= -radius; x--) {
                        for (int y = radius; y >= -radius; y--) {
                            for (int z = radius; z >= -radius; z--) {
                                Block locBlock = target.getRelative(x, y, z);
                                if (!transparent.contains(locBlock.getType())) {
                                    if (!transparent.contains(locBlock.getRelative(BlockFace.UP).getType())) continue;
                                    locBlock.setType(inkling.getWoolMaterial());
                                }
                            }
                        }
                    }
                    armorStand.remove();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    @EventHandler
    public void onRefillInk(PlayerInteractEvent event) {
        if (event.getAction().toString().toLowerCase().contains("right")) return;

        if (!(event.getMaterial() == Material.STICK)) return;

        Player player = event.getPlayer();

        if (reloading.contains(player)) return;

        Inkling inkling = gameManager.getPlayer(player);

        if (inkling.isWaiting()) return;

        reloading.add(player);
        event.setCancelled(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                inkling.refillInkIncrement(25);

                if (inkling.inkFull()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§lRELOADED"));
                    cancel();
                } else {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + inkling.getInkPercentage()));
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }
}
