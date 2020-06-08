package it.polimi.ingsw.utils;

public enum Color {
    White,
    Purple,
    Brown;

    public static char toFirstLetter (int num) {
        return switch (num) {
            case 0 -> 'W';
            case 1 -> 'P';
            case 2 -> 'B';
            default -> throw new IllegalArgumentException();
        };
    }

    public static Color intToColor (int num){
        return switch (num) {
            case 0 -> Color.White;
            case 1 -> Color.Purple;
            case 2 -> Brown;
            default -> throw new IllegalArgumentException();
        };
    }
}
