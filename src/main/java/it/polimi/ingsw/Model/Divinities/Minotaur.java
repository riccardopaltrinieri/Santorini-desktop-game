package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Divinity;

public class Minotaur implements GodPower {
    private final Divinity divinity=Divinity.Minotaur;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {
        Worker minotaurWorker = player.getWorker(worker);

        // If the destination is empty just do a normal move
        if (destination.getIsEmpty()){
            minotaurWorker.move(destination);
        }
        else {
            // If the worker wants to move in the same cell throw exception
            if(destination == player.getWorker(worker).getPosition()) throw new IllegalArgumentException();

            destination.setEmpty(true);
            if (player.getWorker(worker).canMoveTo(destination)) {

                // Iteration for all the players/worker to find the one to push
                for (Player wantedPlayer : player.getGame().getPlayers()) {
                    for (Worker wantedWorker : wantedPlayer.getWorkers()) {

                        if (wantedWorker.getPosition().equals(destination)) {
                            int deltaRow = wantedWorker.getPosition().getNumRow() - minotaurWorker.getPosition().getNumRow();
                            int deltaColumn = wantedWorker.getPosition().getNumColumn() - minotaurWorker.getPosition().getNumColumn();
                            Cell newPosition = wantedWorker.getPosition();
                            Cell pushedDestination = player.getGame().getBoard().getCell(newPosition.getNumRow() + deltaRow, newPosition.getNumColumn() + deltaColumn);
                            wantedWorker.move(pushedDestination);
                            player.getWorker(worker).move(newPosition);
                        }
                    }
                }
            }
            destination.setEmpty(false);
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
