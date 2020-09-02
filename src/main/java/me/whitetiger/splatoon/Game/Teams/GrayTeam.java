package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class GrayTeam implements ITeam {
    
        private static ITeam Instance;
    
        public GrayTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "GrayTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.GRAY_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Gray;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }