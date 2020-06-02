package it.polimi.ingsw.Model;

public enum Color {
    White,
    Purple,
    Brown;

    public static char toFirstLetter (int num) {
        if(num == 0) return 'W';
        else if(num == 1) return 'P';
        else if(num == 2) return 'B';
        else throw new IllegalArgumentException();
    }

    public static Color intToColor (int num){
        if (num==0) return Color.White;
        else if (num==1) return Color.Purple;
        else if (num==2) return Brown;
        else throw new IllegalArgumentException();
    }

    public  static Color stroingToColor (String string){
        String lowString = string.toLowerCase();
        if (lowString.equals("white")){
            return Color.White;
        }
        else if (lowString.equals("purple")){
            return Color.Purple;
        }
        else if (lowString.equals("brown")){
            return Color.Brown;
        }
        else throw  new IllegalArgumentException();
    }
}
