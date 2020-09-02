package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class RedTeam implements ITeam {
    
        private static ITeam Instance;
    
        public RedTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "RedTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.RED_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Red;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }