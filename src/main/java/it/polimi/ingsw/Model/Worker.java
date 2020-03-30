package it.polimi.ingsw.Model;

public class Worker {

    //Attributi
    private Cell position;
    private Player owner;

    //getter e setter

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
