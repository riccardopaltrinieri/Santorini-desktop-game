package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Worker;
import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Prometheus implements GodPower {
    private final Divinity divinity=Divinity.Prometheus;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {

        // The worker cannot move up if he used the power and he could have trapped himself:
        // A canMove check is needed and the flag canMoveUp is set on false remembering
        // what it was before
        boolean oldCanMoveUp = player.getGame().getCanMoveUp();
        player.getGame().setCanMoveUp(false);
        if(!player.canMove()) player.getGame().hasLoser();
        player.getGame().setCanMoveUp(oldCanMoveUp);

        // Then proceed with the move checking if the worker's moving up
        Worker prometheusWorker = player.getWorker(worker);
        if (destination.getLevel() <= prometheusWorker.getPosition().getLevel() )
            player.getWorker(worker).move(destination);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
