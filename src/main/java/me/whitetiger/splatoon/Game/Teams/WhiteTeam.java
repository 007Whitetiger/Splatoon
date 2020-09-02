package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class WhiteTeam implements ITeam {
    
        private static ITeam Instance;
    
        public WhiteTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "WhiteTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.WHITE_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.White;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }