package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LightGrayTeam implements ITeam {
        @Override
        public String getName() {
            return "LightGrayTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIGHT_GRAY_WOOL;
        }
    }