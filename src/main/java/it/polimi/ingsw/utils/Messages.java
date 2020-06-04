package it.polimi.ingsw.utils;

public enum Messages {

    ERROR_PLACE,
    ERROR_MOVE,
    ERROR_BUILD,
    ERROR_ATHENA,
    ERROR_POWER,
    MOVE_MESSAGE,
    BUILD_MESSAGE,
    PLACE_MESSAGE,
    GODPOWER_MESSAGE;

    public static String getMessage(Messages msg) {
        return switch (msg) {
            case ERROR_PLACE -> " can't place here";
            case ERROR_MOVE -> " can't move here";
            case ERROR_BUILD -> " can't build here";
            case ERROR_ATHENA -> " can't move up because Athena's power is active";
            case ERROR_POWER -> " can't use the God Power";
            case GODPOWER_MESSAGE -> " used the God Power";
            default -> null;
        };
    }

    public static String getMessage(Messages msg, int row, int col) {
        return switch (msg) {
            case MOVE_MESSAGE -> " moved a worker in " + row + 'x' + col;
            case BUILD_MESSAGE -> " build in " + row + ' ' + col;
            case PLACE_MESSAGE -> " placed a worker in " + row + 'x' + col;
            default -> null;
        };
    }
}
