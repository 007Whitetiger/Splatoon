/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.whitetiger.splatoon.Utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;

public class Sniper implements LoadGun{
    private final int range = 100;
    private final int damage = 10;
    private final int splat = 2;
    private final PotionEffect slowEffect = new PotionEffect(PotionEffectType.SLOW, 10, 20, true, false, false);

    @Override
    public String getName() {
        return "Sniper";
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.GUN;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getSplashLimit() {
        return 3;
    }

    @Override
    public double getSplash() {
        return 2.5;
    }

    @Override
    public double getCooldown() {
        return 0.2;
    }

    @Override
    public Weapon getWeapon() {
        return this;
    }

    @Override
    public void doCustomBehavior() {

    }

    @Override
    public int getWeaponChargingTime() {
        return 1400;
    }

    @Override
    public ItemStack getWeaponItem() {
        return ItemUtils.fastName(Material.STICK, ChatColor.GREEN + "Sniper");
    }

    @Override
    public void loadInteract(Player player) {
        player.addPotionEffect(slowEffect);
        
    }

    @Override
    public void loadFail(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW);
        
    }

    @Override
    public void loadFinish(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW);
    }
}
