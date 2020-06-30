package it.polimi.ingsw.utils;

public enum Messages {

    ERROR_ATHENA(" can't move up because Athena's power is active"),
    ERROR_BUILD(" can't build here"),
    ERROR_MOVE(" can't move here"),
    ERROR_PLACE(" can't place here"),
    ERROR_POWER(" can't use the God Power"),
    ERROR_WORKER(" this worker can't move"),
    PLACE(" placed a worker"),
    MOVE(" moved a worker"),
    BUILD(" moved a worker"),
    GODPOWER(" used the God Power");

    private final String message;

    Messages(String s) {
        message = s;
    }

    public String getMessage(String playerName) {
        return switch (this) {
            case ERROR_PLACE -> "Error: " + playerName + " can't place here";
            case ERROR_WORKER -> "Error: " + playerName + " this worker can't move";
            case ERROR_MOVE -> "Error: " + playerName + " can't move here";
            case ERROR_BUILD -> "Error: " + playerName + " can't build here";
            case ERROR_ATHENA -> "Error: " + playerName + " can't move up because Athena's power is active";
            case ERROR_POWER -> "Error: " + playerName + " can't use the God Power";
            case GODPOWER -> "Insert " + playerName + " used the God Power";
            default -> null;
        };
    }

    public String getMessage(int row, int col) {
        return switch (this) {
            case MOVE -> " moved a worker in " + row + 'x' + col;
            case BUILD -> " built in " + row + ' ' + col;
            case PLACE -> " placed a worker in " + row + 'x' + col;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return this.message;
    }
}
