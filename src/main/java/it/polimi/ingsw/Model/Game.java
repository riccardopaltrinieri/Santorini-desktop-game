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

            String message = "Insert " + getCurrentPlayer().getName() + " placed a worker in " + (row+1) + "x" + (column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Error: You can't place your worker here", board, this));
        }
        return false;
    }

    public boolean move(int row, int column, int worker) {
        Cell position = getCurrentPlayer().getWorker(worker).getPosition();
        Cell destination = board.getCell(row, column);

        try{
            if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
            getCurrentPlayer().getWorker(worker).move(destination);

            String message = "Insert " + getCurrentPlayer().getName() + " moved a worker in " + row + "x" + column;
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (AthenaException e) {
            sendBoard(new LiteBoard("Error: You can't move up because Athena's power is active", board, this));
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Error: Can't move here", board, this));
        }
        return false;
    }

    public boolean build(int row, int column, int worker) {
        try {
            getCurrentPlayer().getWorker(worker).build(board.getCell(row, column));

            String message = "Insert " + getCurrentPlayer().getName() + " build in" + row + "x" + column;
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Error: Can't build here", board, this));
        }
        return false;
    }

    public boolean useGodPower(int row, int column, int worker) {
        try {
            getCurrentPlayer().getGodPower().execute(getCurrentPlayer(),board.getCell(row,column),worker);

            sendBoard(new LiteBoard("Insert " + getCurrentPlayer().getName() + " used the God Power", board, this));
            return true;

        } catch (AthenaException e) {
            sendBoard(new LiteBoard("Error: You can't move up because Athena's power is active", board, this));
        } catch (IllegalArgumentException e) {
            sendBoard(new LiteBoard("Error: Can't use the Power", board, this));
        }
        return false;
    }

    public void endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();
        sendBoard(new LiteBoard("player " + getCurrentPlayer().getName() + " moves"));
        sendBoard(new LiteBoard("Insert " + getCurrentPlayer().getName() + " update", board, this));
    }


//  ************** GETTER AND SETTER ***********************************

    public int getNumWorkers() {
        int numWorkers = 0;
        for (Player player : players) {
            numWorkers += player.getWorkers().size();
        }
        return numWorkers;
    }
    public Player getCurrentPlayer() {
        return players.get(iterator);
    }

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
    public int getNumPlayer() {
        return numPlayer;
    }
}
