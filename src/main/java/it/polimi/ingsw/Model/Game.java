package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Controller;

public class Game {
    public Game() {
    }

    //attributi
    //alcuni sono commentati perche sono riferimenti a cose ancora da aggiungere
    private int action;
    private int numPlayer;
    private int currentPlayer;
    private int currentTurn;
    private Turn[] order;
    private Controller controller;
    private Board board;
    private Player[] player;

    public void hasLoser() {}

    public void hasWinner() {}


    //getter e setter

    public int getAction(){
        return action;
    }
    public void setAction(int action){
        this.action=action;
    }

    public int getNumPlayer(){
        return numPlayer;
    }
    public void setNumPlayer(int numPlayer){
        this.numPlayer=numPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Turn[] getOrder() {
        return order;
    }
    public void setOrder(Turn[] order) {
        this.order = order;
    }

    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public Player[] getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player[currentPlayer] = player;
        this.player[currentPlayer].setGame(this);
    }
}
