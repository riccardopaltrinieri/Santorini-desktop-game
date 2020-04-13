package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class Minotaur implements GodPower {
    private final Divinity divinity=Divinity.Minotaur;

    @Override
    public void execute(Player player, Cell destination, int worker) throws AthenaException {
        if (destination.getIsEmpty()){
            throw new IllegalArgumentException();
        }
        else {
            destination.setEmpty(true);
            if (player.getWorker(worker).getPosition().canMoveTo(destination)) {
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
                int deltaRow = players.get(wantedPlayer).getWorker(wantedWorker).getPosition().getNumRow()-player.getWorker(worker).getPosition().getNumRow();
                int deltaColumn = players.get(wantedPlayer).getWorker(wantedWorker).getPosition().getNumColumn()-player.getWorker(worker).getPosition().getNumColumn();
                Cell fooPosition = players.get(wantedPlayer).getWorker(wantedWorker).getPosition();
                Cell fooDestination = player.getGame().getBoard().getMap()[fooPosition.getNumRow()+deltaRow][fooPosition.getNumColumn()+deltaColumn];
                players.get(wantedPlayer).getWorker(wantedWorker).move(fooDestination);
                player.getWorker(worker).move(fooPosition);
            }
            destination.setEmpty(false);
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}
