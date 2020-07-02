package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;

/**
 * The main Model class of the project, it contains the references to the players, their workers
 * and the board of the game.
 * For every message coming from the clients the corresponding method is called to change the
 * state of data and move the workers or build somewhere on the board.
 * The basic rules are: <br>
 * _ {@link Player} take turns, starting with the Start Player, who first placed his {@link Worker}.
 * On their turn, they select one of their workers and must move and then build with it. <br>
 * _ If one Workers moves up on top of level 3 during the turn, the player instantly wins! <br>
 * _ A player must always perform a move then build on his turn. If he is unable to, he loses.
 *
 * @see it.polimi.ingsw.Controller.Controller
 * @see Player
 * @see Worker
 */
public class Game extends Observable implements Originator {

    private int numPlayer;
    private int iterator;
    private Board board;
    private ArrayList<Player> players;
    private Boolean canMoveUp;

    /**
     * An object that store the copy of the model’s state. The contents of the memento
     * aren’t accessible to any other object except the one that produced it.
     * @see Originator
     * @see CareTaker
     * @see #Memento(Game)
     */
    private static class Memento {
        public final Board board;
        public final ArrayList<Player> players;
        public Boolean canMoveUp;

        /**
         * Create a {@link Memento} object that can be used after some changes to restore the previous state.
         * @param game is the originator of this memento object
         * @see Memento
         */
        private Memento(Game game) {
            this.canMoveUp = game.getCanMoveUp();

            // Get a copy of the map
            this.board = new Board(game.getBoard().getMapCopy());
            // Create a copy of every player and place their workers on the new map
            this.players = new ArrayList<>();
            for (Player p : game.getPlayers()) {
                Player playerCopy = new Player(p);
                this.players.add(playerCopy);
                for (Worker w : p.getWorkers()) {
                    playerCopy.placeWorkers(board.getCell(
                            w.getPosition().getNumRow(),
                            w.getPosition().getNumColumn()
                    ));
                }
            }
        }
    }

    /**
     * Constructor
     */
    public Game () {
        this.board = new Board();
        this.players = new ArrayList<>(3);
        this.iterator = 0;
        this.canMoveUp=true;
    }

    /**
     * Method that removes the player who can't move the worker anywhere
     */
    public void hasLoser() {
        if(numPlayer == 2) {
            iterator = (iterator + 1) % numPlayer;
            hasWinner();
        } else {

            getCurrentPlayer().getWorker(0).getPosition().setEmpty(true);
            getCurrentPlayer().getWorker(1).getPosition().setEmpty(true);
            getCurrentPlayer().getWorkers().clear();
            sendBoard(new LiteBoard("Loser: " + getCurrentPlayerName() + " loses", board, this));
            players.remove(getCurrentPlayer());
            numPlayer -= 1;
            iterator = iterator % numPlayer;
        }
    }

    /**
     * Notifies all the players the name of the one who won
     */
    public void hasWinner(){
        sendBoard(new LiteBoard("Winner: " + getCurrentPlayerName() + " wins", board, this));
        board.clearAll();
    }

    /**
     * Places a worker on the board for the current player
     * @return true only if the worker has been placed
     */
    public boolean placeWorker(int row, int column) {
        try {
            getCurrentPlayer().placeWorkers(board.getCell(row, column));

            String message = "Insert " + getCurrentPlayerName() + Messages.PLACE.getMessage(row+1, column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;
        } catch (IllegalArgumentException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_PLACE;
            sendBoard(new LiteBoard(message, board, this));
        }
        return false;
    }

    /**
     * Moves the worker into a board cell, if something does not allow that notify the players with a custom message
     * @return true if the worker was moved
     */
    public boolean move(int row, int column, int worker) {

        try {
            if (!getCurrentPlayer().workerCanMove(worker)) throw new IllegalStateException();

            Cell position = getCurrentPlayer().getWorker(worker).getPosition();
            Cell destination = board.getCell(row, column);

            getCurrentPlayer().getWorker(worker).move(destination);
            if (position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();

            String message = "Insert " + getCurrentPlayerName() + Messages.MOVE.getMessage(row + 1, column + 1);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalStateException e){
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_WORKER;
            sendBoard(new LiteBoard(message, board, this));
        } catch (AthenaException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_ATHENA;
            sendBoard(new LiteBoard(message, board, this));
        } catch (IllegalArgumentException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_MOVE;
            sendBoard(new LiteBoard(message, board, this));
        }
        return false;
    }

    /**
     * Builds in a board cell, if something does not allow it notify the players with a custom message
     * @return true if the worker built in the cell
     */
    public boolean build(int row, int column, int worker) {
        try {
            getCurrentPlayer().getWorker(worker).build(board.getCell(row, column));

            String message = "Insert " + getCurrentPlayerName() + Messages.BUILD.getMessage(row+1, column+1);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalArgumentException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_BUILD;
            sendBoard(new LiteBoard(message, board, this));
        }
        return false;
    }

    /**
     * Uses the god power of the current player, if something does not allow it notify the players with a custom message
     * @return true if the god power has been executed
     */
    public boolean useGodPower(int row, int column, int worker) {
        try {
            GodPower god = getCurrentPlayer().getGodPower();

            if( god.getDivinity() == Divinity.Athena || god.getDivinity() == Divinity.Apollo ||
                    god.getDivinity() == Divinity.Pan || god.getDivinity() == Divinity.Artemis ||
                    god.getDivinity() == Divinity.Minotaur) {
                if (!getCurrentPlayer().workerCanMove(worker)) throw new IllegalStateException();
                Cell position = getCurrentPlayer().getWorker(worker).getPosition();
                Cell destination = board.getCell(row, column);

                god.execute(getCurrentPlayer(),board.getCell(row,column),worker);

                if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
                else {
                    String message = "Insert " + getCurrentPlayerName() + Messages.GODPOWER;
                    sendBoard(new LiteBoard(message, board, this));
                }
                return true;
            }

            god.execute(getCurrentPlayer(),board.getCell(row,column),worker);

            String message = "Insert " + getCurrentPlayerName() + Messages.GODPOWER;
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalStateException e){
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_WORKER;
            sendBoard(new LiteBoard(message, board, this));
        } catch (AthenaException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_ATHENA;
            sendBoard(new LiteBoard(message, board, this));
        } catch (IllegalArgumentException e) {
            String message = "Error: " + getCurrentPlayerName() + Messages.ERROR_POWER;
            sendBoard(new LiteBoard(message, board, this));
        }
        return false;
    }

    /**
     * Increment the iterator of players and check if the next player has lose
     * @return true if the game is not ended
     */
    public synchronized boolean endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();

        String message = "Insert " + getCurrentPlayerName() + " update";
        sendBoard(new LiteBoard(message, board, this));
        return true;
    }

    @Override
    public Object createMemento() {
        return new Memento(this);
    }

    @Override
    public void restore(Object obj) {
        Memento stateToRestore = (Memento) obj;
        this.board = stateToRestore.board;
        this.players = stateToRestore.players;
        this.canMoveUp = stateToRestore.canMoveUp;
    }

//  ******************* SPECIAL GETTERS ***************************************

    /**
     * @return the number of worker placed in the board
     */
    public int getNumWorkers() {
        int numWorkers = 0;
        for (Player player : players) {
            numWorkers += player.getWorkers().size();
        }
        return numWorkers;
    }

    /**
     * @return the player who's playing the current turn
     */
    public Player getCurrentPlayer() {
        return players.get(iterator);
    }

    /**
     * @return the name of the player who's playing the current turn
     */
    public String getCurrentPlayerName() {
        return players.get(iterator).getName();
    }

//  ******************* GETTER AND SETTER *************************************

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
