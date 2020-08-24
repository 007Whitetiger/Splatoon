package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LimeTeam implements ITeam {
        @Override
        public String getName() {
            return "LimeTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIME_WOOL;
        }
    }