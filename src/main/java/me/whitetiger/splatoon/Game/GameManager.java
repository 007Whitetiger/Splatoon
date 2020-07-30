/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameManager {
    public HashMap<Player, Inkling> players = new HashMap<>();
    public GameState gameState;
}
