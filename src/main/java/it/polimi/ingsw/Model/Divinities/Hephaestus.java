package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import it.polimi.ingsw.Model.Player;

public class Hephaestus implements GodPower {
    private final Divinity divinity=Divinity.Hephaestus;

    @Override
    public void execute(Player player, Cell destination, int worker) {

    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }
}