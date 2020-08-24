package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class MagentaTeam implements ITeam {
        @Override
        public String getName() {
            return "MagentaTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.MAGENTA_WOOL;
        }
    }