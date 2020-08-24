/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game.Weapons;

public class Custom implements Weapon{
    private int range = 10;
    private int damage = 10;
    private int splash = 1;

    @Override
    public String getName() {
        return "Custom";
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
        return splash;
    }

    @Override
    public Weapon getWeapon() {
        return null;
    }

    @Override
    public void doCustomBehavior() {

    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
