/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

public class Sniper implements Weapon{
    private final int range = 15;
    private final int damage = 15;
    private final int splat = 2;

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
    public int getSplash() {
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
}
