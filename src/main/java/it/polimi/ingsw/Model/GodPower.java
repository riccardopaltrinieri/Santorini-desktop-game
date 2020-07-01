package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.utils.Divinity;

public interface GodPower {

    void execute(Player player, Cell destination, int worker) throws AthenaException;
    Divinity getDivinity();
}
