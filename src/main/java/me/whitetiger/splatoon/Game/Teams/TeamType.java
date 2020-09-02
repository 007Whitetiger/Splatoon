package me.whitetiger.splatoon.Game.Teams;

public enum TeamType {

   White("WHITE"),
   Orange("ORANGE"),
   Magenta("MAGENTA"),
   LightBlue("LIGHTBLUE"),
   Yellow("YELLOW"),
   Lime("LIME"),
   Pink("PINK"),
   Gray("GRAY"),
   LightGray("LIGHTGRAY"),
   Cyan("CYAN"),
   Blue("BLUE"),
   Brown("BROWN"),
   Green("GREEN"),
   Red("RED"),
   Black("BLACK");

   private String name;

   TeamType(String name) {
        this.name = name;
   }

    public String getName() {
        return name;
    }
}