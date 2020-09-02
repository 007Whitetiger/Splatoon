package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class LimeTeam implements ITeam {
    
        private static ITeam Instance;
    
        public LimeTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "LimeTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.LIME_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Lime;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }