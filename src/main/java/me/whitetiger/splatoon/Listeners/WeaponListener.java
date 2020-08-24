/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Listeners;

import com.google.common.collect.Sets;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Utils.MathUtils;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class WeaponListener implements Listener {
    private Splatoon plugin;
    private GameManager gameManager;
    private List<Material> transparent = new ArrayList<>(Arrays.asList(Material.AIR, Material.GRASS, Material.BARRIER, Material.BEACON, Material.TALL_GRASS));

    public WeaponListener(Splatoon plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e) {
        if (!(e.getMaterial() == Material.STICK)) return;
        Player p = e.getPlayer();

        if (p.isSneaking()) return;

        Inkling inkling = gameManager.getPlayer(p);
        if (inkling == null) return;
        Weapon weapon = inkling.getWeapon();

        List<Entity> entities = p.getNearbyEntities(40, 40, 40);
        Entity found = null;
        BlockIterator blocks = new BlockIterator(p, weapon.getRange());
        Block block;
        while (blocks.hasNext() & found == null) {

            block = blocks.next();
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity)) continue;
                if (!p.hasLineOfSight(entity)) continue;
                if (found != null) continue;

                LivingEntity living = (LivingEntity) entity;

                if (living.isDead()) continue;

                double entityX = entity.getLocation().getX();
                double entityY = entity.getLocation().getY();
                double entityZ = entity.getLocation().getZ();

                if ((blockX <= entityX && entityX <= blockX +1) && (blockZ <= entityZ && entityZ <= blockZ+1) && (blockY -1 <= entityY && entityY <= blockY +.75)) {// all values here are for the hitbox
                    Location vector = p.getLocation().subtract(entity.getLocation());

                    if (p.getLocation().distance(living.getLocation()) <= weapon.getRange()) continue;

                    found = entity;

                    p.sendMessage(String.valueOf(weapon.getDamage()));
                    living.getLocation().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, living.getEyeLocation(),10);
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
}
