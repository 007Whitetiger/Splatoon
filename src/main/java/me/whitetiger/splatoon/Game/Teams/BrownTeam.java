package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BrownTeam implements ITeam {
    
        private static ITeam Instance;
    
        public BrownTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "BrownTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BROWN_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Brown;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }