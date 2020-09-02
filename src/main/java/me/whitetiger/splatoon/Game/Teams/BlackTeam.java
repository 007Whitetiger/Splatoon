package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class BlackTeam implements ITeam {
    
        private static ITeam Instance;
    
        public BlackTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "BlackTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.BLACK_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Black;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }