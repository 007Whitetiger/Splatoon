package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BlueTeam implements ITeam {
        @Override
        public String getName() {
            return "BlueTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BLUE_WOOL;
        }
    }