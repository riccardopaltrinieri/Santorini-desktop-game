package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Divinity;

public class Apollo implements GodPower {
    private final Divinity divinity=Divinity.Apollo;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {

        // If the destination is empty just do a normal move
        if (destination.getIsEmpty()){
            player.getWorker(worker).move(destination);
        }
        else {
            // If the worker wants to move in the same cell throw exception
            if(destination == player.getWorker(worker).getPosition()) throw new IllegalArgumentException();

            destination.setEmpty(true);
            if (player.getWorker(worker).canMoveTo(destination)) {

                // Iteration for all the players/worker to find the one to switch
                for (Player wantedPlayer : player.getGame().getPlayers()) {
                    for (Worker wantedWorker : wantedPlayer.getWorkers()) {

                        if (wantedWorker.getPosition().equals(destination)) {
                            // Simple switch of variables
                            Cell exchangePosition = player.getWorker(worker).getPosition();
                            player.getWorker(worker).setPosition(wantedWorker.getPosition());
                            wantedWorker.setPosition(exchangePosition);
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
