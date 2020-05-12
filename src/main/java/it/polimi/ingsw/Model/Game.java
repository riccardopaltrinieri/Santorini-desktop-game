package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.utils.Observable;

import java.util.ArrayList;

public class Game extends Observable {
    private int numPlayer;
    private int iterator;
    private Controller controller;
    private Board board;
    private ArrayList<Player> players;
    private Boolean canMoveUp;

    /**
     * Constructor
     */
    public Game () {
        this.board = new Board();
        this.controller = new Controller(this);
        this.players = new ArrayList<>(3);
        this.iterator = 0;
        this.canMoveUp=true;
    }

    public void hasLoser() {
        if(numPlayer == 2) {
            iterator = (iterator + 1) % numPlayer;
            hasWinner();
        } else {
            notifyObservers(getCurrentPlayer().getName() + " loses");
            getCurrentPlayer().getWorker(0).getPosition().setEmpty(true);
            getCurrentPlayer().getWorker(1).getPosition().setEmpty(true);
            players.remove(getCurrentPlayer());
            numPlayer -= 1;
            iterator = (iterator + 1) % numPlayer;
        }
    }

    public void hasWinner(){
        notifyObservers(getCurrentPlayer().getName() + "wins");
        board.clearAll();
    }

    public void placeWorker(int row, int column) {
        try {
            getCurrentPlayer().placeWorkers(board.getCell(row, column));
        } catch (IllegalArgumentException e) {
            notifyObservers("You can't place your worker here");
        }
    }

    public void move(int row, int column, int worker) {
        Cell position = getCurrentPlayer().getWorker(worker).getPosition();
        Cell destination = board.getCell(row, column);

        try{
            if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
            getCurrentPlayer().getWorker(worker).move(destination);
        } catch (AthenaException e) {
            notifyObservers("You can't move up because Athena's power is active");
        } catch (IllegalArgumentException e) {
            notifyObservers("Can't move here");

        }
    }

    public void build(int row, int column, int worker) {
        try {
            getCurrentPlayer().getWorker(worker).build(board.getCell(row, column));
        } catch (IllegalArgumentException e) {
            notifyObservers("Can't build here");
        }
    }

    public void useGodPower(int row, int column, int worker) {
        try {
            getCurrentPlayer().getGodPower().execute(getCurrentPlayer(),board.getCell(row,column),worker);
        } catch (AthenaException e) {
            notifyObservers("You can't move up because Athena's power is active");
        } catch (IllegalArgumentException e) {
            notifyObservers("Can't use the Power");
        }
    }

    public void endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();
        notifyObservers(getCurrentPlayer().getName() + " moves");
    }

    public Player getCurrentPlayer() {
        return players.get(iterator);
    }

//  ************** GETTER AND SETTER ***********************************

    public Boolean getCanMoveUp() {
        return canMoveUp;
    }
    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
    public int getNumPlayer(){
        return numPlayer;
    }
    public void setNumPlayer(int numPlayer){
        this.numPlayer=numPlayer;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public int getIterator() {
        return iterator;
    }
    public void setIterator(int iterator) {
        this.iterator = iterator;
    }
    public Controller getController() {
        return controller;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
}
