package it.polimi.ingsw.utils;

import it.polimi.ingsw.AthenaException;

import java.util.LinkedList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers;

    public Observable() {
        observers = new LinkedList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) throws AthenaException {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}