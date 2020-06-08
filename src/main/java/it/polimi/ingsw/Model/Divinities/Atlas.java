package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Atlas implements GodPower {
    private final Divinity divinity=Divinity.Atlas;
    private int oldLevel;

    @Override
    public void execute(Player player, Cell destination, int worker) throws IllegalArgumentException {
        if ((player.getWorker(worker).canBuildIn(destination))&&(destination.getLevel()<4)){
            oldLevel = destination.getLevel();
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

    @Override
    public void undo(Player player, Cell oldPosition, int worker, Cell building) {
        building.setLevel(oldLevel);
    }
}
