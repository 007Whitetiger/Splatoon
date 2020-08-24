package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class GreenTeam implements ITeam {
        @Override
        public String getName() {
            return "GreenTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.GREEN_WOOL;
        }
    }