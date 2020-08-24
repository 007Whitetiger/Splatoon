package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class PinkTeam implements ITeam {
        @Override
        public String getName() {
            return "PinkTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.PINK_WOOL;
        }
    }