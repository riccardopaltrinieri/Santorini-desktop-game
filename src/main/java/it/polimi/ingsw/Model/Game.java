package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.View.LiteBoard;
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
            sendBoard(new LiteBoard(getCurrentPlayer().getName() + " loses", board, this));
            getCurrentPlayer().getWorker(0).getPosition().setEmpty(true);
            getCurrentPlayer().getWorker(1).getPosition().setEmpty(true);
            players.remove(getCurrentPlayer());
            numPlayer -= 1;
            iterator = (iterator + 1) % numPlayer;
        }
    }

    public void hasWinner(){
        sendBoard(new LiteBoard(getCurrentPlayer().getName() + "wins", board, this));
        board.clearAll();
    }

    public boolean placeWorker(int row, int column) {
        try {
            getCurrentPlayer().placeWorkers(board.getCell(row, column));
            return true;
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("You can't place your worker here", board, this));
        }
        return false;
    }

    public boolean move(int row, int column, int worker) {
        Cell position = getCurrentPlayer().getWorker(worker).getPosition();
        Cell destination = board.getCell(row, column);

        try{
            if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
            getCurrentPlayer().getWorker(worker).move(destination);
          //  sendBoard(new LiteBoard(getCurrentPlayer().getName() + " update", board, this));
            return true;
        } catch (AthenaException e) {
            sendBoard(new LiteBoard("You can't move up because Athena's power is active", board, this));
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Can't move here", board, this));
        }
        return false;
    }

    public boolean build(int row, int column, int worker) {
        try {
            getCurrentPlayer().getWorker(worker).build(board.getCell(row, column));
        //    sendBoard(new LiteBoard(getCurrentPlayer().getName() + " update", board, this));
            return true;
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Can't build here", board, this));
        }
        return false;
    }

    public boolean useGodPower(int row, int column, int worker) {
        try {
            getCurrentPlayer().getGodPower().execute(getCurrentPlayer(),board.getCell(row,column),worker);
            return true;
        } catch (AthenaException e) {
            sendBoard(new LiteBoard("You can't move up because Athena's power is active", board, this));
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Can't use the Power", board, this));
        }
        return false;
    }

    public void endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();
        sendBoard(new LiteBoard(getCurrentPlayer().getName() + " moves", board, this));
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
    public void setNumPlayer(int numPlayer){
        this.numPlayer=numPlayer;
    }
    public ArrayList<Player> getPlayers() {
        return players;
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
