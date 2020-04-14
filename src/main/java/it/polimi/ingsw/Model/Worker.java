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
     *
     * @param destination is the cell where you want to move the worker
     * @throws IllegalArgumentException if you can't move in the destination cell
     * @throws AthenaException if you are trying to move in a cell whit a level higher than yours but athena's godpower is activated
     */

    public void move (Cell destination) throws IllegalArgumentException, AthenaException {
        if (position.canMoveTo(destination)){
            if ((position.getLevel()==destination.getLevel()-1)&&!(owner.getGame().getCanMoveUp())){
                throw new AthenaException();
            }
            else {
                if ((owner.getGodPower() instanceof Pan)&&(position.getLevel()>destination.getLevel()+1)){
                    owner.getGame().hasWinner();
                }
                position.setEmpty(true);
                position = destination;
                position.setEmpty(false);
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param destination is the cell where you are trying to build
     * @throws IllegalArgumentException if you can't build in the destination cell for any reason
     */

    public void build (Cell destination) throws  IllegalArgumentException{
        if (position.canBuildIn(destination)){
            destination.setLevel(destination.getLevel()+1);
        }
        else{
            throw new IllegalArgumentException();
        }
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
