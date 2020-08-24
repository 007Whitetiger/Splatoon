/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Weapons.Sniper;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Inkling {
    private Boolean alive;
    private Weapon weapon;
    private Player bukkitPlayer;
    private Material woolMat;

    public Inkling(Player p, Weapon weapon, Material woolMaterial) {
        this.bukkitPlayer = p;
        this.weapon = weapon;
        this.woolMat = woolMaterial;
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
}
