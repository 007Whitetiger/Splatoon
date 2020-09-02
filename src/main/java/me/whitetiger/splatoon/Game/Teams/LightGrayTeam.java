package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LightGrayTeam implements ITeam {
    
        private static ITeam Instance;
    
        public LightGrayTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "LightGrayTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIGHT_GRAY_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.LightGray;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }