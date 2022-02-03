/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Utils;

import org.bukkit.util.Vector;

public class MathUtils {
    public static double square(double x) {
        return x * x;
    }

    public static int checkHowManyNumbersOfVectorEquals(Vector vector1, Vector vector2) {
        int timesEqauls = 0;
        
        // System.out.printf("vec1: %d-%d-%d vec2: %d-%d-%d\n", vector1.getBlockX(), vector1.getBlockY(), vector1.getBlockZ(), vector2.getBlockX(), vector2.getBlockY(), vector2.getBlockZ());
        if (vector1.getBlockX() == vector2.getBlockX()) timesEqauls++;
        if (vector1.getBlockY() == vector2.getBlockY()) timesEqauls++;
        if (vector1.getBlockZ() == vector2.getBlockZ()) timesEqauls++;
        return timesEqauls;
    }
}
