/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Weapons.Weapon;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameManager {
    public HashMap<Player, Inkling> players = new HashMap<>();
    public GameState gameState;

    public GameManager() {
        this.gameState = GameState.WAITING;
    }

    public GameState getGameState() {
        return gameState;
    }

    public HashMap<Player, Inkling> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<Player, Inkling> players) {
        this.players = players;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void addPlayer(Player p, Weapon weapon) {
        Inkling newPlayer = new Inkling(p, weapon);
        this.players.put(p, newPlayer);
    }
    public Inkling getPlayer(Player p) {
        if (players.containsKey(p)) {
            return this.players.get(p);
        }
        return null;
    }
}
