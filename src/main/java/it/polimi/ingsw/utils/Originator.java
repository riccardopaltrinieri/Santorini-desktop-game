package it.polimi.ingsw.utils;

/**
 * The Originator class can produce snapshots of its own state with {@link #createMemento()},
 * as well as restore its state from snapshots when needed with {@link #restore(Object)}.
 *
 * @see CareTaker
 */
public interface Originator {

    /**
     * Produce and a snapshot of its own state and save it in a memento object.
     * The Memento object is "opaque" to the client(CareTaker) and all other objects
     * @return the Memento object which contains the state
     */
    Object createMemento();

    /**
     * Restore the state from the Memento object
     * @param obj contains the state to restore
     */
    void restore(Object obj);
}
