package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Atlas implements GodPower {
    private final Divinity divinity=Divinity.Atlas;

    @Override
    public void execute(Player player, Cell destination, int worker) throws IllegalArgumentException {
        if ((player.getWorker(worker).canBuildIn(destination))&&(destination.getLevel()<4)){
            destination.setLevel(4);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
