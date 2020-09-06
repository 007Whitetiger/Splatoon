/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

import me.whitetiger.splatoon.Utils.Cooldowns;

public interface Weapon {


    String getName();
    WeaponType getWeaponType();

    int getRange();
    int getDamage();
    int getSplash();
    double getCooldown();

    Weapon getWeapon();
    void doCustomBehavior();

    default void addCooldown() {
        Cooldowns.addCooldownSeconds(this, getCooldown());
    }
}
