/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Teams.ITeam;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Inkling {

    private Weapon weapon;
    private final Player bukkitPlayer;
    private final ITeam team;

    private boolean alive;
    private int ink;
    private final int maxInk = 200;
    private int lives = 3;

    public Inkling(Player p, Weapon weapon, ITeam team) {
        this.bukkitPlayer = p;
        this.weapon = weapon;
        this.ink = maxInk;
        this.team = team;

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
        return team.getWoolColor();
    }

    public int getInk() {
        return ink;
    }

    public ITeam getTeam() {
        return team;
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

    public boolean inkFull() {
        return ink == maxInk;
    }

    public int getInkPercentage() {
        return (int)((double)ink / (double)maxInk * 100);
    }

    public void kill() {
        this.lives -= 1;
        if (lives == 0) {
            this.alive = false;
        }
    }

    public boolean isDead() {
        return !alive;
    }

}
