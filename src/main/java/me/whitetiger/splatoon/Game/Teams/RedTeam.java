package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class RedTeam implements ITeam {
        @Override
        public String getName() {
            return "RedTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.RED_WOOL;
        }
    }