package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BlueTeam implements ITeam {
    
        private static ITeam Instance;
    
        public BlueTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "BlueTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BLUE_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Blue;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }