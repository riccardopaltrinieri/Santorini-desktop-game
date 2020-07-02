package it.polimi.ingsw.utils;

import it.polimi.ingsw.View.LiteBoard;

/**
 * The method {@link #update(String)} of the classes with this interface will be called
 * when the class they're observing notify them. <br>
 * Neither the observers nor the observed classes know each others.
 *
 * @see Observable
 */
public interface Observer {

    /**
     * Method called when the class is notified by the one observed
     * @param message is passed from the observable
     *
     * @see Observable
     */
    void update(String message);
    void newBoard(LiteBoard board);
}
