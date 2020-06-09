package it.polimi.ingsw.utils;

public enum Color {
    White,
    Purple,
    Brown;

    public static final String RESET = "\u001B[0m";

    public static char toFirstLetter (int numColor) {
        return switch (numColor) {
            case 0 -> 'W';
            case 1 -> 'P';
            case 2 -> 'B';
            default -> throw new IllegalArgumentException("Color must be 0|1|2");
        };
    }

    public static Color intToColor (int numColor){
        return switch (numColor) {
            case 0 -> Color.White;
            case 1 -> Color.Purple;
            case 2 -> Brown;
            default -> throw new IllegalArgumentException("Color must be 0|1|2");
        };
    }

    public static String toANSICode(int numColor) {
        return switch (numColor) {
            case 0 -> "\u001B[1m";
            case 1 -> "\u001B[35m";
            case 2 -> "\u001B[31m";
            default -> throw new IllegalArgumentException("Color must be 0|1|2");
        };
    }
}
