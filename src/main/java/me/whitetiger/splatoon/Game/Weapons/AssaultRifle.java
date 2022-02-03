/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.whitetiger.splatoon.Utils.ItemUtils;
import net.md_5.bungee.api.ChatColor;

public class AssaultRifle implements Weapon {


    @Override
    public String getName() {
        return "assault";
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.GUN;
    }

    @Override
    public int getRange() {
        return 30;
    }

    @Override
    public int getDamage() {
        return 3;
    }

    @Override
    public int getSplashLimit() {
        return 3;
    }

    @Override
    public double getSplash() {
        return 1;
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
        return ItemUtils.fastName(Material.STICK, ChatColor.RED + "AssaultRiffle");
    }

    @Override
    public double getCooldown() {
        return 0;
    }
}
