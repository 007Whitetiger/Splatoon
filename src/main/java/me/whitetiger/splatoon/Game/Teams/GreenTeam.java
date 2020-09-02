package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class GreenTeam implements ITeam {
    
        private static ITeam Instance;
    
        public GreenTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "GreenTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.GREEN_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Green;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }