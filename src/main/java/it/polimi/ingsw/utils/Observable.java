package it.polimi.ingsw.utils;

import it.polimi.ingsw.View.LiteBoard;

import java.util.LinkedList;
import java.util.List;

/**
 * An observable class will be observed by a list of {@link Observer}s that will wait for
 * this to notify them. <br>
 * Neither the observers nor the observed classes know each others.
 *
 * @see Observer
 */
public abstract class Observable {
    private final List<Observer> observers;

    public Observable() {
        observers = new LinkedList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void sendBoard(LiteBoard board) {
        for (Observer observer : observers) {
            observer.newBoard(board);
        }
    }
}