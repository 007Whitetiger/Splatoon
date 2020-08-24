package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class WhiteTeam implements ITeam {
        @Override
        public String getName() {
            return "WhiteTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.WHITE_WOOL;
        }
    }