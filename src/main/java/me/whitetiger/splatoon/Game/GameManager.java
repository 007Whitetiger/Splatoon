/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Game;

import me.whitetiger.splatoon.Game.Teams.*;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
    private HashMap<Player, Inkling> players = new HashMap<>();
    private List<ITeam> teams = new ArrayList<>();
    private GameState gameState;

    public GameManager() {
        this.gameState = GameState.WAITING;

        this.teams.add(new BlackTeam());
        this.teams.add(new BlueTeam());
        this.teams.add(new BrownTeam());
        this.teams.add(new CyanTeam());
        this.teams.add(new GrayTeam());
        this.teams.add(new GreenTeam());
        this.teams.add(new LightBlueTeam());
        this.teams.add(new LightGrayTeam());
        this.teams.add(new LimeTeam());
        this.teams.add(new MagentaTeam());
        this.teams.add(new OrangeTeam());
        this.teams.add(new PinkTeam());
        this.teams.add(new RedTeam());
        this.teams.add(new WhiteTeam());
        this.teams.add(new YellowTeam());


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

    public void addPlayer(Player p, Weapon weapon, ITeam team) {
        Inkling newPlayer = new Inkling(p, weapon, team);
        this.players.put(p, newPlayer);
    }
    public Inkling getPlayer(Player p) {
        if (players.containsKey(p)) {
            return this.players.get(p);
        }
        return null;
    }

    public List<ITeam> getTeams() {
        return teams;
    }
}
