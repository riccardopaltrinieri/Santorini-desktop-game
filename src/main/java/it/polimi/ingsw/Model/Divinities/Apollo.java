package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class Apollo implements GodPower {
    private final Divinity divinity=Divinity.Apollo;

    @Override
    public void execute(Player player, Cell destination, int worker) {
        if (destination.getIsEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            destination.setEmpty(true);
            if (player.getWorker(worker).canMoveTo(destination)) {
                ArrayList<Player> players = player.getGame().getPlayers();
                int wantedPlayer = 0;
                int wantedWorker = 0;
                while ((wantedPlayer < players.size()) &&
                        !(players.get(wantedPlayer).getWorker(wantedWorker).getPosition().equals(destination))) {
                    if (wantedWorker == 0) {
                        wantedWorker = 1;
                    } else {
                        wantedPlayer++;
                        wantedWorker = 0;
                    }
                    if (wantedPlayer == players.size()) {
                        throw new IllegalArgumentException();
                    }
                }
                Cell exchangePosition = player.getWorker(worker).getPosition();
                player.getWorker(worker).setPosition(players.get(wantedPlayer).getWorker(wantedWorker).getPosition());
                players.get(wantedPlayer).getWorker(wantedWorker).setPosition(exchangePosition);
            }
            destination.setEmpty(false);
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
