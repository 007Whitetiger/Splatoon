package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BrownTeam implements ITeam {
        @Override
        public String getName() {
            return "BrownTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BROWN_WOOL;
        }
    }