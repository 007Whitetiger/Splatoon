package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class PinkTeam implements ITeam {
    
        private static ITeam Instance;
    
        public PinkTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "PinkTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.PINK_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Pink;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }