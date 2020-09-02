package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class YellowTeam implements ITeam {
    
        private static ITeam Instance;
    
        public YellowTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "YellowTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.YELLOW_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Yellow;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }