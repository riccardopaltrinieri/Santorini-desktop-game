package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.Messages;
import it.polimi.ingsw.utils.Observable;

import java.util.ArrayList;

public class Game extends Observable {

    private int numPlayer;
    private int iterator;
    private final Board board;
    private final ArrayList<Player> players;
    private Boolean canMoveUp;

    /**
     * Constructor
     */
    public Game () {
        this.board = new Board();
        this.players = new ArrayList<>(3);
        this.iterator = 0;
        this.canMoveUp=true;
    }

    public void hasLoser() {
        if(numPlayer == 2) {
            iterator = (iterator + 1) % numPlayer;
            hasWinner();
        } else {

            getCurrentPlayer().getWorker(0).getPosition().setEmpty(true);
            getCurrentPlayer().getWorker(1).getPosition().setEmpty(true);
            getCurrentPlayer().getWorkers().clear();
            sendBoard(new LiteBoard("Loser: " + getCurrentPlayer().getName() + " loses", board, this));
            players.remove(getCurrentPlayer());
            numPlayer -= 1;
            iterator = iterator % numPlayer;
        }
    }

    public void hasWinner(){
        sendBoard(new LiteBoard("Winner: " + getCurrentPlayer().getName() + " wins", board, this));
        board.clearAll();
    }

    public boolean placeWorker(int row, int column) {
        try {
            getCurrentPlayer().placeWorkers(board.getCell(row, column));

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.PLACE_MESSAGE, row+1, column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;
        } catch (IllegalArgumentException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_PLACE);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        }
        return false;
    }

    public boolean move(int row, int column, int worker) {
        Cell position = getCurrentPlayer().getWorker(worker).getPosition();
        Cell destination = board.getCell(row, column);

        try{
            if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
            getCurrentPlayer().getWorker(worker).move(destination);

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.MOVE_MESSAGE, row+1, column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (AthenaException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_ATHENA);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        } catch (IllegalArgumentException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_MOVE);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        }
        return false;
    }

    public boolean build(int row, int column, int worker) {
        try {
            getCurrentPlayer().getWorker(worker).build(board.getCell(row, column));

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.BUILD_MESSAGE, row+1, column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalArgumentException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_BUILD);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        }
        return false;
    }

    public boolean useGodPower(int row, int column, int worker) {
        try {
            getCurrentPlayer().getGodPower().execute(getCurrentPlayer(),board.getCell(row,column),worker);

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.GODPOWER_MESSAGE);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (AthenaException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_ATHENA);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        } catch (IllegalArgumentException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_POWER);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        }
        return false;
    }

    public synchronized void endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();
        sendBoard(new LiteBoard("Insert " + getCurrentPlayer().getName() + " update", board, this));
    }


//  *************** SPECIAL GETTERS **********
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

//  ************** GETTER AND SETTER ***********************************


    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Boolean getCanMoveUp() {
        return canMoveUp;
    }
    public Board getBoard() {
        return board;
    }
    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
    public void setNumPlayer(int numPlayer){
        this.numPlayer=numPlayer;
    }
}
