package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Controller;

public class Game {
    public Game() {
    }

    //attributi
    //alcuni sono commentati perche sono riferimenti a cose ancora da aggiungere
    private int action;
    private int numPlayer;
    private String currentPlayer;
    private Turn currentTurn;
    private Turn[] order;
    private Controller controller;
    private Tab tab;
    private Player player;


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

    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }
    public void setCurrentTurn(Turn currentTurn) {
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

    public Tab getTab() {
        return tab;
    }
    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
