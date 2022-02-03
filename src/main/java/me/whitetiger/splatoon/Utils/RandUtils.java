package me.whitetiger.splatoon.Utils;

import java.util.Random;

public class RandUtils {

    private static Random random = new Random();

    /**
     * Get random bool with chance
     * @param chance chance 0 <= chance < 100
     * @return a bool
     */
    public static boolean getRandomBoolWithChance(int chance) {
        int randomInt = random.nextInt(100);
        return chance < randomInt;
    }
    
}
