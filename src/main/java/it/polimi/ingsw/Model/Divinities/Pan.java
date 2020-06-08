package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Worker;
import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Pan implements GodPower {
    private final Divinity divinity=Divinity.Pan;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {

        Worker panWorker = player.getWorker(worker);

        // If the worker move down of 2 levels he wins
        if (panWorker.canMoveTo(destination)) {
            if (panWorker.getPosition().getLevel() > destination.getLevel() + 1)
                player.getGame().hasWinner();
            else
                panWorker.move(destination);
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }

    @Override
    public void undo(Player player, Cell oldPosition, int worker, Cell building) {

    }
}
