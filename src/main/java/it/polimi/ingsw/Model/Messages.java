package it.polimi.ingsw.Model;

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
        switch (msg){
            case ERROR_PLACE:
                return " can't place here";
            case ERROR_MOVE:
                return " can't move here";
            case ERROR_BUILD:
                return " can't build here";
            case ERROR_ATHENA:
                return " can't move up because Athena's power is active";
            case ERROR_POWER:
                return " can't use the God Power";
            case GODPOWER_MESSAGE:
                return " used the God Power";
        }
        return null;
    }

    public static String getMessage(Messages msg, int row, int col) {
        switch (msg) {
            case MOVE_MESSAGE:
                return " moved a worker in " + row + 'x' + col;
            case BUILD_MESSAGE:
                return " build in " + row + ' ' + col;
            case PLACE_MESSAGE:
                return " placed a worker in " + row + 'x' + col;
        }
        return null;
    }
}
