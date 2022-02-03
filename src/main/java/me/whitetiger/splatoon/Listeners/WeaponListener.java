/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Listeners;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Game.Weapons.LoadGun;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Utils.Cooldowns;
import me.whitetiger.splatoon.Utils.DevUtils;
import me.whitetiger.splatoon.Utils.MathUtils;
import me.whitetiger.splatoon.Utils.RandUtils;
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
import org.bukkit.util.RayTraceResult;

import java.util.*;

public class WeaponListener implements Listener {
    private final Splatoon plugin;
    private final GameManager gameManager;
    private final List<Material> transparent = new ArrayList<>(Arrays.asList(Material.AIR, Material.GRASS, Material.BARRIER, Material.BEACON, Material.TALL_GRASS));

    private final List<Player> reloading = new ArrayList<>();


    private static class ChargeTimeStamp {
        private final BukkitRunnable failedRunnable;
        private final Long timeStamp;
        public ChargeTimeStamp(Long timeStamp, BukkitRunnable failedRunnable) {
            this.failedRunnable = failedRunnable;
            this.timeStamp = timeStamp;
        }
    }

    private final HashMap<Player, HashMap<Weapon, ChargeTimeStamp>> userToLastChargeWeapon = new HashMap<>();
    private final HashMap<Player, Long> totalChargeTime = new HashMap<>();

    public WeaponListener(Splatoon plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFireEvent(PlayerInteractEvent e) {

        long startWeaponListener = System.currentTimeMillis();

        if (e.getAction().toString().toLowerCase().contains("left")) return;

        Player p = e.getPlayer();

        if (p.isSneaking()) return;

        Inkling inkling = gameManager.getPlayer(p);

        if (inkling == null) return;

        Weapon weapon = inkling.getWeapon(e.getItem());

        if (weapon == null) return;
        
        e.setCancelled(true);

        if (inkling.isWaiting() || inkling.isSwimming()) return;

        

        if (inkling.getInk() < 10) {
            p.sendMessage("§cYou're out of ink!");
            return;
        }
        
        

        if (Cooldowns.isCooldowned(weapon)) {
            p.sendMessage("§cThis weapon is on cooldown!");
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c" + Cooldowns.getCooldown(weapon) + "S"));
            return;
        }

        
        switch (weapon.getWeaponType()) {
            case GUN:
                if (weapon instanceof LoadGun) {
                    chargeWeapon(p, inkling, (LoadGun) weapon);
                    return;
                } else {
                    useWeapon(p, inkling, weapon);
                }
                break;
            case GRENADE:
                useGrenade(p, inkling, weapon);
                break;
        }
        weapon.addCooldown();

        long endWeaponListener = System.currentTimeMillis();

        DevUtils.debug(String.format("Processing weaponListener took: %d ms", endWeaponListener - startWeaponListener));

    }

    private void addTimeStamp(Player player, LoadGun weapon, Long timeStamp) {
        BukkitRunnable failedRunnable = new BukkitRunnable(){

            @Override
            public void run() {
                player.sendMessage(ChatColor.RED + "Charge Up Failed");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Charge Up Failed"));
                weapon.loadFail(player);
            }
            
        };
        failedRunnable.runTaskLater(plugin, 5);
        weapon.loadInteract(player);
        this.userToLastChargeWeapon.get(player).put(weapon, new ChargeTimeStamp(timeStamp, failedRunnable));
    }

    private void chargeWeapon(Player player, Inkling inkling, LoadGun weapon) {
        long currentMilliSeconds = System.currentTimeMillis();

        userToLastChargeWeapon.putIfAbsent(player, new HashMap<>());

        ChargeTimeStamp lastChargeThisWeapon = userToLastChargeWeapon.get(player).get(weapon);
        Long startedTimeLong = totalChargeTime.get(player);

        if (lastChargeThisWeapon == null || startedTimeLong == null) {
            addTimeStamp(player, weapon, currentMilliSeconds);
            totalChargeTime.put(player, currentMilliSeconds);
            return;
        }
        boolean validSmallIncrement = currentMilliSeconds - lastChargeThisWeapon.timeStamp <= 250;
        boolean validTotalIncrement =  currentMilliSeconds - startedTimeLong >= weapon.getWeaponChargingTime();

        lastChargeThisWeapon.failedRunnable.cancel();

        if (validSmallIncrement && validTotalIncrement) {
            player.sendMessage(ChatColor.GREEN + "Shot Fired!");
            useWeapon(player, inkling, weapon);
            weapon.addCooldown();
            weapon.loadFinish(player);

            

        } else if (validSmallIncrement) {

            BaseComponent[] chatMessage = TextComponent.fromLegacyText(ChatColor.GOLD + Double.toString(Math.round(((currentMilliSeconds - startedTimeLong) / (double) weapon.getWeaponChargingTime()) * 100)) +  "%");

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, chatMessage);
            addTimeStamp(player, weapon, currentMilliSeconds);
            
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Charging!"));
            totalChargeTime.put(player, currentMilliSeconds);
            addTimeStamp(player, weapon, currentMilliSeconds);
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

        ArmorStand shotArmorStand = p.getWorld().spawn(p.getEyeLocation(), ArmorStand.class);

        new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                if (index < 8) {
                    shotArmorStand.setVelocity(p.getEyeLocation().getDirection().normalize());
                }
                RayTraceResult rayTraceResult = shotArmorStand.rayTraceBlocks(0.5, FluidCollisionMode.NEVER);
                List<Entity> nearbyEntities = shotArmorStand.getNearbyEntities(0.2, 0.2, 0.2);
                Block blockBelow = shotArmorStand.getLocation().getBlock().getRelative(BlockFace.DOWN);

                if (rayTraceResult != null || (!blockBelow.isEmpty() && shotArmorStand.isOnGround())) {
                    shotArmorStand.remove();
                    Block targetBlock;
                    BlockFace targetFace;
                    if (rayTraceResult != null) {
                        targetBlock = rayTraceResult.getHitBlock();
                        targetFace = rayTraceResult.getHitBlockFace();
                    } else {
                        targetBlock = blockBelow;
                        targetFace = BlockFace.DOWN;
                    }
                    assert targetBlock != null;
                    assert targetFace != null;
                    doWeaponBlockSplash(targetBlock, targetFace, weapon, inkling);
                    cancel();
                }
                else if (!nearbyEntities.isEmpty() && (nearbyEntities.get(0) != p || nearbyEntities.size() > 1)) {
                    shotArmorStand.remove();
                    Entity shotEntity = nearbyEntities.get(0);
                    if (shotEntity == p) {
                        shotEntity = nearbyEntities.get(1);
                    }
                    if (!(shotEntity instanceof LivingEntity)) return;
                    LivingEntity livingShotEntity = (LivingEntity) shotEntity;
                    Objects.requireNonNull(livingShotEntity.getLocation().getWorld()).spawnParticle(Particle.FIREWORKS_SPARK, livingShotEntity.getEyeLocation(),10);

                    if (livingShotEntity instanceof Player) {
                        Inkling enemy = gameManager.getPlayer((Player) livingShotEntity);
                        if (enemy.getTeam() == inkling.getTeam()) return;
                    }
                    livingShotEntity.damage(weapon.getDamage());
                    cancel();
                }
                index++;
            }
        }.runTaskTimer(plugin, 0, 1);

    }

    private void useWeaponOld(Player p, Inkling inkling, Weapon weapon) {
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

        p.sendMessage(String.valueOf(weapon.getDamage()));
        
        
        

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
                    DevUtils.debug(String.valueOf(p.getLocation().distance(living.getLocation())));
                    if (p.getLocation().distance(living.getLocation()) > weapon.getRange()) continue;

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
       



        RayTraceResult targetTrace = p.rayTraceBlocks(weapon.getRange());
    

        if (targetTrace == null) return;

        Block target = targetTrace.getHitBlock();
        BlockFace targetFace = targetTrace.getHitBlockFace();

        assert target != null;
        assert targetFace != null;
        doWeaponBlockSplash(target, targetFace, weapon, inkling);
        weapon.doCustomBehavior();
        
    }

    private void doWeaponBlockSplash(Block target, BlockFace targetFace, Weapon weapon, Inkling inkling) {
        World targetWorld = target.getWorld();
        BlockFace oppisteTargetFace = targetFace.getOppositeFace();

        Location minimalLocation = target.getLocation();
        Location maximalLocation = target.getLocation();

        double weaponSplash = weapon.getSplash();
        double weaponSplashLimit = weapon.getSplashLimit();
        double flooredWeaponSplash = Math.floor(weaponSplash);
        double weaponSplashRest = weaponSplash - flooredWeaponSplash;

        boolean isRandomEdge = weaponSplashRest != 0;

        switch (targetFace) {
            case NORTH:
            case SOUTH: {
                minimalLocation.subtract(flooredWeaponSplash, flooredWeaponSplash, 0);
                maximalLocation.add(flooredWeaponSplash, flooredWeaponSplash, 0);
                if (isRandomEdge) {
                    minimalLocation.subtract(1, 1, 0);
                    maximalLocation.add(1, 1, 0);
                }
                break;
            }
            case WEST:
            case EAST: {
                minimalLocation.subtract(0, flooredWeaponSplash, flooredWeaponSplash);
                maximalLocation.add(0, flooredWeaponSplash, flooredWeaponSplash);
                if (isRandomEdge) {
                    minimalLocation.subtract(0, 1, 1);
                    maximalLocation.add(0, 1, 1);
                }
                break;
            }
            case UP:
            case DOWN: {

                minimalLocation.subtract(flooredWeaponSplash, 0, flooredWeaponSplash);
                maximalLocation.add(flooredWeaponSplash, 0, flooredWeaponSplash);
                if (isRandomEdge) {
                    minimalLocation.subtract(1, 0, 1);
                    maximalLocation.add(1, 0, 1);
                }
                break;
            }
            default: {
                plugin.getLogger().severe("WTF");
                throw new Error("BlockFace " + targetFace + " was not found!");
            }

        }

        for (int x = minimalLocation.getBlockX(); x <= maximalLocation.getBlockX(); x++) {
            for (int y = minimalLocation.getBlockY(); y <= maximalLocation.getBlockY(); y++) {
                for (int z = minimalLocation.getBlockZ(); z <= maximalLocation.getBlockZ(); z++) {


                    Block tempBlock = targetWorld.getBlockAt(x, y, z);

                    Location tempBlockLocation = tempBlock.getLocation();

                    /*
                     System.out.printf("Current: %d-%d-%d", tempBlockLocation.toVector().getBlockX(), tempBlockLocation.toVector().getBlockY(), tempBlockLocation.toVector().getBlockZ());
                     System.out.println(MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), minimalLocation.toVector()));
                     System.out.println(MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), maximalLocation.toVector()));

                     System.out.printf("Total: %b   First: %b    Second:%b\n", isRandomEdge && (MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), minimalLocation.toVector()) >= 2 || MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), maximalLocation.toVector()) >= 2),  MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), minimalLocation.toVector()) >= 2, MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), maximalLocation.toVector()) >= 2);
                     */
                    if (isRandomEdge && (MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), minimalLocation.toVector()) >= 2 || MathUtils.checkHowManyNumbersOfVectorEquals(tempBlockLocation.toVector(), maximalLocation.toVector()) >= 2)) {
                        if (RandUtils.getRandomBoolWithChance((int) Math.round(weaponSplashRest * 100))) continue;
                    }
                    int tempI = 0;
                    while (tempBlock.getType() == Material.AIR && tempI < weaponSplashLimit) {
                        tempBlock = tempBlock.getRelative(oppisteTargetFace);
                        tempI++;
                    }
                    if (tempBlock.getType() != Material.AIR) {
                        tempBlock.setType(inkling.getWoolMaterial());
                    }
                }
            }
        }
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

                    int radius = weapon.getSplashLimit();

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

    @EventHandler()
    public void onRefillInk(PlayerInteractEvent event) {
        if (event.getAction().toString().toLowerCase().contains("right")) return;

        Player player = event.getPlayer();
        
        Inkling inkling = gameManager.getPlayer(player);

        if (inkling == null) return;

        Weapon weapon = inkling.getWeapon(event.getItem());
        if (weapon == null) return;

        event.setCancelled(true);

        if (inkling.isWaiting() || inkling.isSwimming()) return;

        if (reloading.contains(player)) return;


        reloading.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {

                if (inkling.isWaiting() || inkling.isSwimming()) {
                    reloading.remove(player);
                    cancel();
                    return;
                }

                inkling.refillInkIncrement(25);

                if (inkling.inkFull()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§lRELOADED"));
                    reloading.remove(player);
                    cancel();
                } else {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a" + inkling.getInkPercentage()));
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }
}
