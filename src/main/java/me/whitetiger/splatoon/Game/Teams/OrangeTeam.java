package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class OrangeTeam implements ITeam {
    
        private static ITeam Instance;
    
        public OrangeTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "OrangeTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.ORANGE_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Orange;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }