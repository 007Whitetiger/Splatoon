/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Weapons.Sniper;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import org.bukkit.entity.Player;

public class Inkling {
    public Boolean alive;
    public Weapon weapon;
    public Player bukkitPlayer;

    public Inkling(Player p, Weapon weapon) {
        this.bukkitPlayer = p;
        this.weapon = weapon;
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
}
