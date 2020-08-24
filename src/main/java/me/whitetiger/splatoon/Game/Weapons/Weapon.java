/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

public interface Weapon {


    String getName();

    int getRange();
    int getDamage();
    int getSplash();

    Weapon getWeapon();
    void doCustomBehavior();
}
