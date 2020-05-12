package it.polimi.ingsw.utils;

import it.polimi.ingsw.View.LiteBoard;

public interface Observer {
    void update(String message);
    void newBoard(LiteBoard board);
}
