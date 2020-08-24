package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class YellowTeam implements ITeam {
        @Override
        public String getName() {
            return "YellowTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.YELLOW_WOOL;
        }
    }