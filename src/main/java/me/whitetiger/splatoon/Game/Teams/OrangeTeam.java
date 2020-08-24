package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class OrangeTeam implements ITeam {
        @Override
        public String getName() {
            return "OrangeTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.ORANGE_WOOL;
        }
    }