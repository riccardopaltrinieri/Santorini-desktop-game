package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Player {

    private String name;
    private Color color;
    private Divinity divinity;
    private ArrayList<Worker> workers;
    private Game game;
    private GodPower godPower;

    /**
     * Constructor
     */
    public Player (String name, Color color, Game game) {
        this.name = name;
        this.color = color;
        this.game = game;
        this.workers = new ArrayList<>(2);

    }

    /**
     * Place the workers on the map with the worker constructor
     */
    public void placeWorkers(int row, int column) {
        Board board = this.game.getBoard();
        workers.add(new Worker( board.getCell(row,column), this));
    }

    /**
     * Examine the cell around the player's workers
     * @return boolean that indicates if the player can make any move
     */
    public boolean canMove() {
        Board board = this.game.getBoard();

        for(int i = 0; i < 2; i++) {
            Cell pos = workers.get(i).getPosition();
            //check all the cells from the one top-left to the one down-right, if just one is ok the player can move
            for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
                for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                    if (pos.canMoveTo(board.getCell(row,col))) return true;
        }
        return false;
    }

    //***************** GETTER AND SETTER ******************

    public String getName() {
        return name;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Divinity getDivinity() {
        return divinity;
    }
    public void setDivinity(Divinity divinity) {
        this.divinity = divinity;
    }
    public Worker getWorker(int numWorker) {
        return workers.get(numWorker);
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public GodPower getGodPower() {
        return godPower;
    }
    public void setGodPower(GodPower godPower) {
        this.godPower = godPower;
    }


}
