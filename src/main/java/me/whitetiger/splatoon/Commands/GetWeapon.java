package me.whitetiger.splatoon.Commands;

import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.Teams.BlackTeam;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import me.whitetiger.splatoon.Splatoon;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetWeapon implements CommandExecutor {

    private final Splatoon plugin = Splatoon.getInstance();
    private final GameManager gameManager = plugin.getGameManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Class<? extends Weapon> weaponClass = (Class<? extends Weapon>) Class.forName(Weapon.class.getPackage().getName() + "." + args[0]);

            Weapon weapon = weaponClass.newInstance();

            gameManager.addPlayer((Player) sender, weapon, BlackTeam.getInstance());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
