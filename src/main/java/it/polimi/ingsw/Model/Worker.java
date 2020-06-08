package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Divinities.Pan;

public class Worker {

    private Cell position;
    private Player owner;

    /**
     * it's the constructor of the class
     * @param position is the cell where you want to place your worker
     * @param owner is the owner of the worker
     */
    public Worker(Cell position, Player owner){
        if( position.getIsEmpty()) {
            this.position = position;
            this.owner = owner;
            position.setEmpty(false);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Move the worker in the destination cell
     * @param destination is the cell where you want to move the worker
     * @throws IllegalArgumentException if you can't move in the destination cell
     * @throws AthenaException if you are trying to move in a cell whit a level higher than yours but athena's godpower is activated
     */
    public void move (Cell destination) throws IllegalArgumentException, AthenaException {
        if (canMoveTo(destination)){

            if ((position.getLevel()==destination.getLevel()-1)&&!(owner.getGame().getCanMoveUp())){
                throw new AthenaException();
            }
            else {
                position.setEmpty(true);
                position = destination;
                position.setEmpty(false);
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Increments the level of the cell where you want to build
     * @param destination is the cell where you are trying to build
     * @throws IllegalArgumentException if you can't build in the destination cell for any reason
     */
    public void build (Cell destination) throws  IllegalArgumentException{
        if (canBuildIn(destination)){
            destination.setLevel(destination.getLevel()+1);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * the destination must be inside the board, one of the 8 cell adiacent the worker,
     * not the same cell as worker's position, can be maximum one level higher or zero level higher if athena's power is active
     *
     * @param destination is the cell that you want to know if it's reachable
     * @return true if the cell is reachable or false if it's not
     */
    public boolean canMoveTo(Cell destination) {
        // the destination must be inside the board
        return  (destination.getNumRow() >= 0) && (destination.getNumRow() <= 4) &&
                (destination.getNumColumn() >= 0) && (destination.getNumColumn() <= 4) &&

                // one of the 8 cell near the worker
                (destination.getNumRow() >= position.getNumRow() - 1) &&
                (destination.getNumRow() <= position.getNumRow() + 1) &&
                (destination.getNumColumn() >= position.getNumColumn() - 1) &&
                (destination.getNumColumn() <= position.getNumColumn() + 1) &&
                // should not be the same cell as worker's position
                (destination.getIsEmpty()&& (!position.equals(destination))) &&
                // should be maximum 1 level higher and at level 3
                (destination.getLevel() <= position.getLevel() + 1) &&
                (destination.getLevel() < 4);
    }

    /**
     * @param destination is the cell where you want to know if you can build in
     * @return true if you can build in the destination cell or false if you can't
     */
    public boolean canBuildIn(Cell destination){
        return ((destination.getNumRow() >= 0) && (destination.getNumRow() <= 4) &&
                (destination.getNumColumn() >= 0) && (destination.getNumColumn() <= 4) &&
                (destination.getNumRow() >= position.getNumRow() - 1) &&
                (destination.getNumRow() <= position.getNumRow() + 1) &&
                (destination.getNumColumn() >= position.getNumColumn() - 1) &&
                (destination.getNumColumn() <= position.getNumColumn() + 1) &&
                (destination.getIsEmpty()) &&
                (destination.getLevel() < 4) &&
                (!position.equals(destination)));
    }

//  ************** GETTER AND SETTER *******************************

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
