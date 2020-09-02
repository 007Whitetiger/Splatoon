lis = ["WhiteTeam", "OrangeTeam", "MagentaTeam", "LightBlueTeam", "YellowTeam", "LimeTeam",
       "PinkTeam", "GrayTeam", "LightGrayTeam", "CyanTeam", "BlueTeam", "BrownTeam", "GreenTeam",
       "RedTeam", "BlackTeam"]

try:

    with open(f"TeamType.java", "w") as f:
        enum = """package me.whitetiger.splatoon.Game.Teams;

public enum TeamType {\n\n"""
        for item in lis:
            item = str(item.replace("Team", ""))
            enum += "   " + item + '("' + item.upper() + '"),\n'

        enum += "\n}"
        f.write(enum)
except:
    print("didn't work")
    input()
