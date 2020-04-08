package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.utils.Observable;

public class Game extends Observable {
    private int numPlayer;
    private int currentPlayer;
    private Controller controller;
    private Board board;
    private Player[] players;
    private Boolean canMoveUp;

    /**
     * Constructor
     */
    public Game () {
        this.board = new Board();
        this.controller = new Controller(this);
    }

    public void hasLoser() {

    }

    public void hasWinner() {
        notifyObservers(players[currentPlayer].getName() + "wins");
        board.clearAll();
    }

    public void move(int row, int column, int worker) {
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

    public void useGodPower(int row, int column, int worker) {
        getPlayer().getGodPower().execute();
    }

    public void endTurn() {
        currentPlayer ++;
        if (!getPlayer().canMove()){
            notifyObservers(players[currentPlayer].getName() + "lose");
            hasLoser();
        } else {
            notifyObservers(players[currentPlayer].getName() + "moves");
        }
    }

    public Player getPlayer() {
        return players[currentPlayer];
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
    public Player[] getPlayers() {
        return players;
    }
    public void setPlayer(Player player) {
        this.players[currentPlayer] = player;
        this.players[currentPlayer].setGame(this);
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
