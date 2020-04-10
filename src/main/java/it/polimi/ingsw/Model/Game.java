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

    public void hasWinner() throws AthenaException {
        notifyObservers(players.get(currentPlayer).getName() + "wins");
        board.clearAll();
    }

    public void move(int row, int column, int worker) throws AthenaException {
        if(board.getCell(row, column).getLevel() == 3) hasWinner();
        try{
            getPlayer().getWorker(worker).move(board.getCell(row, column));
        } catch (AthenaException e) {
            notifyObservers("athenaException");
        }
    }

    public void build(int row, int column, int worker) {
        getPlayer().getWorker(worker).build(board.getCell(row, column));
    }

    public void useGodPower(int row, int column, int worker) throws AthenaException {
        getPlayer().getGodPower().execute(getPlayer(),board.getCell(row,column),worker);
    }

    public void endTurn() throws AthenaException {
        currentPlayer = (currentPlayer + 1) % 3;
        if (!getPlayer().canMove()){
            notifyObservers(getPlayer().getName() + "lose");
            hasLoser();
        } else {
            notifyObservers(getPlayer().getName() + "moves");
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
