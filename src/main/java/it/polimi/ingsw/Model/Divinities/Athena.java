package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Athena implements GodPower {
    private final Divinity divinity=Divinity.Athena;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {
        if (destination.getLevel()>player.getWorker(worker).getPosition().getLevel()){
            player.getGame().setCanMoveUp(false);
        }
        else{
            player.getGame().setCanMoveUp(true);
        }
        player.getWorker(worker).move(destination);

    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
