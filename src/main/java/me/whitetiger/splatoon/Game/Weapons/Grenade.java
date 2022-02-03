package me.whitetiger.splatoon.Game.Weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class Grenade implements Weapon {
    @Override
    public String getName() {
        return "Grenade";
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.GRENADE;
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public int getDamage() {
        return 10;
    }


    
    @Override
    public int getSplashLimit() {
        return 2;
    }

    @Override
    public double getSplash() {
        return 2;
    }

    @Override
    public double getCooldown() {
        return 5;
    }

    @Override
    public Weapon getWeapon() {
        return this;
    }

    @Override
    public void doCustomBehavior() {

    }

    @Override
    public ItemStack getWeaponItem() {
        return new ItemStack(Material.EGG);
    }
}
