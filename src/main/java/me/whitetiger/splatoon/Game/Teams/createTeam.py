
lis = ["WhiteTeam", "OrangeTeam", "MagentaTeam", "LightBlueTeam", "YellowTeam", "LimeTeam",
       "PinkTeam", "GrayTeam", "LightGrayTeam", "CyanTeam", "BlueTeam", "BrownTeam", "GreenTeam",
       "RedTeam", "BlackTeam"]

try:
    for item in lis:
           with open(f"{item}.java", "w") as f:
               f.write("""package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class """ + item + """ implements ITeam {
    
        private static ITeam Instance;
    
        public """ + item + """() {
            Instance = this;
        }
    
        @Override
        public String getName() {
            return """ + '"' + item + '"' + """;
        }
    
        @Override
        public Material getWoolColor() {
            return Material.""" + item.upper().replace("TEAM", "_WOOL") + """;
        }
        
        @Override
        public TeamType getType() {
            return TeamType.""" + item.replace("Team", "") + """;
        }
        
        public static ITeam getInstance() {
            return Instance;
        }
        
        
    }""")
except:
    print("didn't work")
    input()
