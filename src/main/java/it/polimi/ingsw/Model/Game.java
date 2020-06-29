package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;

public class Game extends Observable implements Originator {

    private int numPlayer;
    private int iterator;
    private Board board;
    private ArrayList<Player> players;
    private Boolean canMoveUp;

    private static class Memento {
        public final Board board;
        public final ArrayList<Player> players;
        public Boolean canMoveUp;

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

        try {
            if (!getCurrentPlayer().workerCanMove(worker)) throw new IllegalStateException();

            Cell position = getCurrentPlayer().getWorker(worker).getPosition();
            Cell destination = board.getCell(row, column);

            getCurrentPlayer().getWorker(worker).move(destination);
            if (position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.MOVE_MESSAGE, row + 1, column + 1);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalStateException e){
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_WORKER);
            sendBoard(new LiteBoard("Error: " + message, board, this));
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
            GodPower god = getCurrentPlayer().getGodPower();

            if( god.getDivinity() == Divinity.Athena || god.getDivinity() == Divinity.Apollo ||
                    god.getDivinity() == Divinity.Pan || god.getDivinity() == Divinity.Artemis ||
                    god.getDivinity() == Divinity.Minotaur) {
                if (!getCurrentPlayer().workerCanMove(worker)) throw new IllegalStateException();
                Cell position = getCurrentPlayer().getWorker(worker).getPosition();
                Cell destination = board.getCell(row, column);
                if(position.getLevel() < 3 && destination.getLevel() == 3) hasWinner();
            }

            god.execute(getCurrentPlayer(),board.getCell(row,column),worker);

            String message = "Insert " + getCurrentPlayer().getName() + Messages.getMessage(Messages.GODPOWER_MESSAGE);
            sendBoard(new LiteBoard(message, board, this));
            return true;

        } catch (IllegalStateException e){
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_WORKER);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        } catch (AthenaException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_ATHENA);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        } catch (IllegalArgumentException e) {
            String message = getCurrentPlayer().getName() + Messages.getMessage(Messages.ERROR_POWER);
            sendBoard(new LiteBoard("Error: " + message, board, this));
        }
        return false;
    }

    public synchronized boolean endTurn() {
        iterator = (iterator + 1) % numPlayer;
        if (!getCurrentPlayer().canMove()) hasLoser();

        sendBoard(new LiteBoard("Insert " + getCurrentPlayer().getName() + " update", board, this));
        return true;
    }

    @Override
    public Object save() {
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
