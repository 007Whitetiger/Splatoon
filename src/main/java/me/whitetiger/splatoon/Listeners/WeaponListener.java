/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.MathUtils;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

public class WeaponListener implements Listener {
    public Splatoon plugin;
    public GameManager gameManager;

    public WeaponListener(Splatoon plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e) {
        if (!(e.getMaterial() == Material.STICK)) return;
        Player p = e.getPlayer();

        List<Entity> entities = p.getNearbyEntities(20, 20, 20);
        Entity found = null;
        BlockIterator blocks = new BlockIterator(p, 20);
        Block block;
        while (blocks.hasNext() & found == null) {

            block = blocks.next();
            System.out.println(block);
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (Entity entity : entities) {
                System.out.println(entity);
                if (!p.hasLineOfSight(entity)) continue;
                if (found != null) continue;

                double ex = entity.getLocation().getX();
                double ey = entity.getLocation().getY();
                double ez = entity.getLocation().getZ();

                if ((bx <= ex && ex <= bx +1) && (bz <= ez && ez <= bz+1) && (by-1 <= ey && ey <= by +.75)) {// all values here are for the hitbox
                    Location vector = p.getLocation().subtract(entity.getLocation());
                    double xrange = Math.sqrt(MathUtils.square(vector.getX()) + MathUtils.square(vector.getY()));
                    double range = Math.sqrt(MathUtils.square(xrange) + MathUtils.square(vector.getY()));
                    found = entity;
                    System.out.println(range);
                    if (range <= 20) p.sendMessage("YES");
                    break;
                }
            }
        }
    }

}
