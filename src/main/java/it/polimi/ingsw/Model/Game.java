package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.utils.Observable;

import java.util.ArrayList;

public class Game extends Observable {
    private int numPlayer;
    private int currentPlayer;
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
        this.currentPlayer = 0;
        this.canMoveUp=true;
    }

    public void hasLoser() {

    }

    public void hasWinner(){
        notifyObservers(players.get(currentPlayer).getName() + "wins");
        board.clearAll();
    }

    public void placeWorker(int row, int column) {
        try {
            getPlayer().placeWorkers(board.getCell(row, column));
        } catch (IllegalArgumentException e) {
            notifyObservers("cantPlace");
        }
    }

    public void move(int row, int column, int worker) {
        Cell position = getPlayer().getWorker(worker).getPosition();
        Cell destination = board.getCell(row, column);

        try{
            if(position.getLevel() != 3 && destination.getLevel() == 3) hasWinner();
            getPlayer().getWorker(worker).move(destination);
        } catch (AthenaException e) {
            notifyObservers("athenaException");
        } catch (IllegalArgumentException e) {
            notifyObservers("cantMove");
        }
    }

    public void build(int row, int column, int worker) {
        try {
            getPlayer().getWorker(worker).build(board.getCell(row, column));
        } catch (IllegalArgumentException e) {
            notifyObservers("cantBuild");
        }
    }

    public void useGodPower(int row, int column, int worker) {
        try {
            getPlayer().getGodPower().execute(getPlayer(),board.getCell(row,column),worker);
        } catch (AthenaException e) {
            notifyObservers("athenaException");
        } catch (IllegalArgumentException e) {
            notifyObservers("cantUsePower");
        }
    }

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % this.numPlayer;
        if (!getPlayer().canMove()){
            notifyObservers(getPlayer().getName() + " loses");
            hasLoser();
        } else {
            notifyObservers(getPlayer().getName() + " moves");
        }
    }

    public Player getPlayer() {
        return players.get(currentPlayer);
    }
    public void addPlayer(Player player) {
        this.players.add(player);
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
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
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
