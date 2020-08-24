package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class CyanTeam implements ITeam {
        @Override
        public String getName() {
            return "CyanTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.CYAN_WOOL;
        }
    }