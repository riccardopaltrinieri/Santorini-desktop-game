package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Prometheus implements GodPower {
    private final Divinity divinity=Divinity.Prometheus;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {
        player.canMove();
        if (!(destination.getLevel()>player.getWorker(worker).getPosition().getLevel())){
            player.getWorker(worker).move(destination);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
