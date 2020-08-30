/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Inkling {
    private Boolean alive;
    private Weapon weapon;
    private Player bukkitPlayer;
    private Material woolMat;
    private int ink;
    private int maxInk = 200;

    public Inkling(Player p, Weapon weapon, Material woolMaterial) {
        this.bukkitPlayer = p;
        this.weapon = weapon;
        this.woolMat = woolMaterial;
        this.ink = maxInk;

    }


    public Weapon getWeapon() {
        return weapon;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public Material getWoolMaterial() {
        return woolMat;
    }

    public int getInk() {
        return ink;
    }

    public void useWeapon() {
        ink -= 10;
    }

    public void refillInkFull() {
        ink = maxInk;
    }

    public void refillInkIncrement(int increment) {
        if (ink + increment >= maxInk) {
            ink = maxInk;
        } else {
            ink += increment;
        }
    }

    public int getInkPercentage() {
        System.out.println(ink);
        System.out.println(maxInk);
        System.out.println(ink / maxInk * 100);
        System.out.println(ink / maxInk);
        return (int)((double)ink / (double)maxInk * 100);
    }
}
