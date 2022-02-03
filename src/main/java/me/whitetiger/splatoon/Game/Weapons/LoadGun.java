package me.whitetiger.splatoon.Game.Weapons;

import org.bukkit.entity.Player;

public interface LoadGun extends Weapon {

    int getWeaponChargingTime();

    void loadInteract(Player player);
    void loadFail(Player player);
    void loadFinish(Player player);

}
