package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Artemis implements GodPower {
    private final Divinity divinity=Divinity.Artemis;
    private Boolean firstTime = true;
    private Cell firstPosition;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {
        player.canMove();
        if (firstTime){
            firstPosition = player.getWorker(worker).getPosition();
            player.getWorker(worker).move(destination);
            firstTime=false;
        }
        else if (firstPosition.equals(destination)){
            throw new IllegalArgumentException();
        }
        else{
            player.getWorker(worker).move(destination);
            firstTime=true;
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
