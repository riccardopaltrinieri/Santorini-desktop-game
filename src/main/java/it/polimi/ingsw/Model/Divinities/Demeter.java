package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Demeter implements GodPower {
    private final Divinity divinity=Divinity.Demeter;
    private Boolean firstTime = true;
    private Cell firstCostruction;

    @Override
    public void execute(Player player, Cell destination, int worker) {
        if (firstTime){
            firstCostruction = destination;
            player.getWorker(worker).build(destination);
            firstTime=false;
        }
        else if (firstCostruction.equals(destination)){
            throw new IllegalArgumentException();
        }
        else{
            player.getWorker(worker).build(destination);
            firstTime=true;
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
