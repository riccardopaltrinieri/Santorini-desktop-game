package it.polimi.ingsw.utils;

/**
 * (From https://en.wikipedia.org/wiki/Memento_pattern)
 * A caretaker can request a memento from the originator (to save its internal state) through the method {@link #save(Originator)}
 * and pass a memento back to the originator (to restore to a previous state) through the method {@link #undo(Originator)}.
 *
 * @see Originator
 */
public abstract class CareTaker {

    private Object memento;

    /**
     * Store data relatives to an {@link Originator} state in a memento object without knowing the interface
     * @param originator creates the stored object
     */
    protected void save(Originator originator) {
        this.memento = originator.createMemento();
    }

    /**
     * Makes the {@link Originator} restore its old state from the memento object stored previously
     * @param  originator the class that's going to restore the state
     */
    protected void undo(Originator originator) {
        originator.restore(memento);
    }
}
