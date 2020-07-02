package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Divinities.*;
import it.polimi.ingsw.utils.Color;

import java.util.ArrayList;

/**
 * A class containing all the data of a game player like his name, color, the god power
 * he chose at the start of the game and his workers.
 * @see Game
 * @see Worker
 */
public class Player {

    private final String name;
    private final Color color;
    private final ArrayList<Worker> workers;
    private final Game game;
    private GodPower godPower;

    /**
     * Constructor
     */
    public Player (String name, Color color, Game game) {
        this.name = name;
        this.color = color;
        this.game = game;
        this.workers = new ArrayList<>(2);
        this.game.getPlayers().add(this);
    }

    public Player(Player player) {
        this.game = player.getGame();
        this.name = player.getName();
        this.color = player.getColor();
        this.workers = new ArrayList<>(2);
        this.godPower = player.godPower;
    }

    /**
     * Place the workers on the map with the worker constructor
     * @throws IllegalArgumentException if there are already 2 workers
     */
    public void placeWorkers(Cell destination) {
        if (workers.size()< 2) workers.add(new Worker( destination, this));
        else throw new IllegalArgumentException();
    }

    /**
     * Examine the cell around the player's workers
     * @return true if the player can make at least one move
     */
    public boolean canMove() {

        if (workers.size() == 0) return true;

        return workerCanMove(0) || workerCanMove(1);
    }

    /**
     * Examine the cell around a worker to check if he can move
     * @return true if is there at least one cell around the worker where he can move
     */
    public boolean workerCanMove(int worker) {

        Board board = game.getBoard();

        Cell pos = getWorker(worker).getPosition();
        //check all the cells from the one top-left to the one down-right, if just one is ok the worker can move
        for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
            for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                if(0 <= row && row < 5 && 0 <= col && col < 5)
                    if (getWorker(worker).canMoveTo(board.getCell(row,col)))
                        return true;

        return false;
    }

    //***************** GETTER AND SETTER ******************

    public String getName() {
        return name;
    }
    public Worker getWorker(int numWorker) {
        return workers.get(numWorker);
    }
    public ArrayList<Worker> getWorkers() {
        return workers;
    }
    public Game getGame() {
        return game;
    }
    public GodPower getGodPower() {
        return godPower;
    }
    public void setGodPower(GodPower godPower) {
        this.godPower = godPower;
    }
    public void setGodPower(String godPower){
        switch (godPower.toLowerCase()) {
            case ("apollo") -> this.godPower = new Apollo();
            case ("artemis") -> this.godPower = new Artemis();
            case ("athena") -> this.godPower = new Athena();
            case ("atlas") -> this.godPower = new Atlas();
            case ("default") -> this.godPower = new DefaultDivinity();
            case ("demeter") -> this.godPower = new Demeter();
            case ("hephaestus") -> this.godPower = new Hephaestus();
            case ("minotaur") -> this.godPower = new Minotaur();
            case ("pan") -> this.godPower = new Pan();
            case ("prometheus") -> this.godPower = new Prometheus();
        }
    }
    public Color getColor() { return color; }
}
