package it.polimi.ingsw.View;

/**
 * The class will receive a message containing all the needed data from the socket,
 * show it to the user through the implemented UI and the method {@link #update(LiteBoard)}
 * and return the user input back to the socket.
 */
public interface UserInterface {

    /**
     * Method that get as input the board to show to the player and asks to him to execute
     * the action he must execute in that moment (according to an internal Finite State Machine
     * which follow the identical one on the server). <br>
     * The action inserted by the player is returned to be sent to the server
     * @param incomingMessage that will be showed to the player based on the data written in it
     * @return the action of the player already checked
     */
    String update(LiteBoard incomingMessage);
}
