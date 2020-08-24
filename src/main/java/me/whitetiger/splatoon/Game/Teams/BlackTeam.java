package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BlackTeam implements ITeam {
        @Override
        public String getName() {
            return "BlackTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BLACK_WOOL;
        }
    }