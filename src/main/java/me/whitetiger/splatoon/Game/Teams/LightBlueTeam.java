package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LightBlueTeam implements ITeam {
        @Override
        public String getName() {
            return "LightBlueTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIGHT_BLUE_WOOL;
        }
    }