package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Divinity;

public class Hephaestus implements GodPower {
    private final Divinity divinity=Divinity.Hephaestus;

    @Override
    public void execute(Player player, Cell destination, int worker) throws IllegalArgumentException{
        if (destination.getLevel()<2){
            player.getWorker(worker).build(destination);
            player.getWorker(worker).build(destination);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Divinity getDivinity() {
        return this.divinity;
    }

    @Override
    public void undo(Player player, Cell oldPosition, int worker, Cell building) {
        building.setLevel(building.getLevel()-2);
    }
}
