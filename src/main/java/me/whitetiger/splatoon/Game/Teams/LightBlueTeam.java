package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LightBlueTeam implements ITeam {
    
        private static ITeam Instance;
    
        public LightBlueTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "LightBlueTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIGHT_BLUE_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.LightBlue;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }