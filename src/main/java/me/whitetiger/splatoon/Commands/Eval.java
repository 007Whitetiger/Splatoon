package me.whitetiger.splatoon.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.whitetiger.splatoon.Splatoon;
import me.whitetiger.splatoon.Game.GameManager;
import me.whitetiger.splatoon.Game.StartManager;
import me.whitetiger.splatoon.Game.Inkling;
import me.whitetiger.splatoon.Game.Teams.BlackTeam;
import me.whitetiger.splatoon.Game.Teams.TeamType;
import me.whitetiger.splatoon.Game.Weapons.AssaultRifle;
import me.whitetiger.splatoon.Game.Weapons.Custom;
import me.whitetiger.splatoon.Game.Weapons.Weapon;
import net.md_5.bungee.api.ChatColor;

public class Eval implements TabExecutor {
    public Splatoon plugin = Splatoon.getInstance();
    public GameManager gameManager = plugin.getGameManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can ony use this command as a player!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            if (gameManager.getPlayer(player) == null) {
                reset(player, args);
            } else {
                sender.sendMessage("Please add a valid sub-command");
            }
            return true;
        }
        

        Inkling inkling = gameManager.getPlayer(player);

        inkling = gameManager.getPlayer(player);

        switch(args[0]) {
            case "reset": {
                reset(player, args);
                player.sendMessage(ChatColor.GREEN + "Resetting your character done!");
                break;
            }
            case "start": {
                new StartManager(gameManager.getTeams().get(0), gameManager.getTeams().get(1), 20);
                player.sendMessage(ChatColor.GREEN + "Started game!");
                break;
            }
            case "end": {
                plugin.getEndGameManager().endGame();
                break;
            }
            case "changeTeam": {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please add a Team name!");
                    break;
                }
                
                try {
                    TeamType teamType = null;
                    teamType = TeamType.valueOf(args[1]);
                    inkling.setTeam(gameManager.getTeam(teamType));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Please add a valid team name!");
                }
                break;
            }
            case "customWeapon": {
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Please add a range and damage");
                    break;
                }
                try {
                    int range = Integer.parseInt(args[1]);
                    int damage = Integer.parseInt(args[2]);

                    Custom customWeapon = new Custom(range, damage);

                    inkling.addWeapon(customWeapon);
                    break;
                    

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_RED + "Please add a valid range and damage");
                    break;
                }
            }
            case "refill": {
                inkling.refillInkFull();
                break;
            }
            case "maxInk": {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please add a max ink value!");
                    break;
                }

                try {
                    int maxInk = Integer.parseInt(args[1]);
                    inkling.setMaxInk(maxInk);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_RED + "Please add a valid max ink number!");
                    
                }
                break;
            }
            case "infInk": {
                inkling.setMaxInk(Integer.MAX_VALUE);
                inkling.refillInkFull();
                player.sendMessage(ChatColor.GREEN + "activated inf ink");
                break;
            }
            case "weapon": {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please add a valid name!");
                    break;
                }
                try {
                    Class<?> weaponClass = Class.forName("me.whitetiger.splatoon.Game.Weapons." + args[1]);
                
                    Weapon weapon = (Weapon) weaponClass.newInstance();
                    inkling.addWeapon(weapon);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                    ignored.printStackTrace();
                    player.sendMessage(ChatColor.DARK_RED + args[1] + ChatColor.RED + " is not a valid weapon name!");
                }
                break;
            }
            default: {
                player.sendMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " is not a valid sub-command!");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return null;
        if (args.length < 2) {
            return Arrays.asList("start", "changeTeam", "reset", "customWeapon", "maxInk", "refill", "infInk", "end", "weapon");
        }
        return null;
    }

    private void reset(Player player, String[] args) {
        AssaultRifle defaultWeapon = new AssaultRifle();
        TeamType currentTeamType = TeamType.Black;

        if (args.length > 1) {
            currentTeamType = TeamType.valueOf(args[1]);
        }

        

        try {
            gameManager.addPlayer(player, defaultWeapon, gameManager.getTeam(currentTeamType));
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + args[1] + " is not a valid Team!");
        }
    }

    
}
