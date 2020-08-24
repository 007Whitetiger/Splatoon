
lis = ["WhiteTeam", "OrangeTeam", "MagentaTeam", "LightBlueTeam", "YellowTeam", "LimeTeam",
       "PinkTeam", "GrayTeam", "LightGrayTeam", "CyanTeam", "BlueTeam", "BrownTeam", "GreenTeam",
       "RedTeam", "BlackTeam"]

try:
    for item in lis:
           with open(f"{item}.java", "w") as f:
               f.write("""package me.whitetiger.splatoon.Game.Teams;
    
    import org.bukkit.Material;
    
    public class """ + item + """ implements ITeam {
        @Override
        public String getName() {
            return """ + '"' + item + '"' + """;
        }
    
        @Override
        public Material getWoolColor() {
            return Material.""" + item.upper().replace("TEAM", "_WOOL") + """;
        }
    }""")
except:
    print("didn't work")
    input()
