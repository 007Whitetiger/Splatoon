package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class CyanTeam implements ITeam {
    
        private static ITeam Instance;
    
        public CyanTeam() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return "CyanTeam";
        }
    
        @Override
        public Material getWoolColor() {
            return Material.CYAN_WOOL;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.Cyan;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }