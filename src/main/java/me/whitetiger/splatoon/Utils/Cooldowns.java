package me.whitetiger.splatoon.Utils;

import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Cooldowns {

    private static Splatoon plugin = Splatoon.getInstance();

    public static HashMap<Weapon, Double> cooldowns = new HashMap<>();

    public static void addCooldownSeconds(Weapon weapon, double seconds) {
        addCooldownTicks(weapon, seconds * 20);
    }

    public static void addCooldownTicks(Weapon weapon, double ticks) {
        if (ticks <= 0) return;
        if (weapon == null) throw new IllegalArgumentException("Weapon is null!");

        cooldowns.put(weapon, ticks);

        new BukkitRunnable() {
            @Override
            public void run() {
                cooldowns.put(weapon, cooldowns.get(weapon) - 1);

                if (cooldowns.get(weapon) <= 0) {
                    cooldowns.remove(weapon);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    public static boolean isCooldowned(Weapon weapon) {
        return cooldowns.containsKey(weapon);
    }

    public static double getCooldown(Weapon weapon) {
        if (cooldowns.containsKey(weapon)) {
            return cooldowns.get(weapon) / 20;
        }
        return 0;
    }
}
