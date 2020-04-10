package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;

public interface GodPower {

    public void execute(Player player,Cell destination,int worker) throws AthenaException;
    public Divinity getDivinity();
}
