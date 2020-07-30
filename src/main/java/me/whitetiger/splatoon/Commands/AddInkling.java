/* ---------------------------------------------------------------------------------------------------------------------
Copyright (c) 2020 007Whitetiger (Stijn Te Baerts) -- developer.whitetiger@gmail.com
This file and all other files associated with this file are owned by me (Stijn Te Baerts).
Please create your own code or ask me for permission at the email above
--------------------------------------------------------------------------------------------------------------------- */
package me.whitetiger.splatoon.Commands;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Weapons.Custom;
import me.whitetiger.splatoon.Game.Weapons.Sniper;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddInkling implements CommandExecutor {
    public Splatoon plugin = Splatoon.instance;
    public GameManager gameManager = plugin.getGameManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        assert sender instanceof Player;
        Custom weapon = new Custom();

        System.out.println(args.length);

        if (args.length == 1) {
            weapon.setDamage(Integer.parseInt(args[0]));
        } else if (args.length == 2) {
            weapon.setDamage(Integer.parseInt(args[0]));
            weapon.setRange(Integer.parseInt(args[1]));
        }
        this.gameManager.addPlayer((Player) sender, weapon);
        return true;
    }
}
