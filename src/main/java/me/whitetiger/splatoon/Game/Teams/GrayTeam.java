package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class GrayTeam implements ITeam {
        @Override
        public String getName() {
            return "GrayTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.GRAY_WOOL;
        }
    }