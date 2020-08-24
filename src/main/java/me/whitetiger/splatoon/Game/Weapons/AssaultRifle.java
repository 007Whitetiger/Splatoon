/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

public class AssaultRifle implements Weapon {


    @Override
    public String getName() {
        return "assault";
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public int getDamage() {
        return 15;
    }

    @Override
    public int getSplash() {
        return 1;
    }


    @Override
    public Weapon getWeapon() {
        return this;
    }

    @Override
    public void doCustomBehavior() {

    }
}
