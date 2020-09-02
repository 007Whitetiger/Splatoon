package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class MagentaTeam implements ITeam {
    
        private static ITeam Instance;
    
        public MagentaTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "MagentaTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.MAGENTA_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Magenta;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }