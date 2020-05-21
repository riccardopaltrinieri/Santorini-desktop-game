package it.polimi.ingsw.Model;

public enum Color {
    Red,
    Yellow,
    Green;

    public static char toFirstLetter (int num) {
        if(num == 0) return 'R';
        else if(num == 1) return 'Y';
        else if(num == 2) return 'G';
        else throw new IllegalArgumentException();
    }
}
