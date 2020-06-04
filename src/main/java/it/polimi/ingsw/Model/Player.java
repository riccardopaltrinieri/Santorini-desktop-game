package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Divinities.*;
import it.polimi.ingsw.utils.Color;

import java.util.ArrayList;

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

    /**
     * Place the workers on the map with the worker constructor
     */
    public void placeWorkers(Cell destination) {
        if (workers.size()< 2) workers.add(new Worker( destination, this));
        else throw new IllegalArgumentException();
    }

    /**
     * Examine the cell around the player's workers
     * @return boolean that indicates if the player can make any move
     */
    public boolean canMove() {
        if (workers.size() == 0) return true;

        Board board = this.game.getBoard();

        for(int i = 0; i < 2; i++) {
            Cell pos = workers.get(i).getPosition();
            //check all the cells from the one top-left to the one down-right, if just one is ok the player can move
            for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
                for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                    if(0 <= row && row < 5 && 0 <= col && col < 5)
                        if (workers.get(i).canMoveTo(board.getCell(row,col)))
                            return true;
        }
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
