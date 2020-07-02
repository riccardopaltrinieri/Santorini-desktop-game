package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.utils.Divinity;

/**
 * This class represents the god power that a player can choose at the start of the game.
 * It contains a method ({@link #execute(Player, Cell, int)}) that is used instead of the normal
 * move/build
 * @see Game
 * @see Player
 * @see Worker
 */
public interface GodPower {

    /**
     * This method is used from the player when he wants to use the power of the God
     * instead of making a move or a build
     * @param player who's making the action
     * @param destination where to move or build with the god power
     * @param worker who's moving or building
     * @throws AthenaException if it's moving up but Athena's power is active
     */
    void execute(Player player, Cell destination, int worker) throws AthenaException;

    Divinity getDivinity();
}
