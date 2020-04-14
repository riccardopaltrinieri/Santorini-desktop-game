package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DemeterTest {
    GodPower test = new Demeter();

    @Test
    public void testExecute(){
        Game game = new Game();
        Board board = game.getBoard();
        Player player = new Player("test player", Color.Green, game);
        player.placeWorkers(board.getCell(1,1));
        Cell firstDestination = board.getCell(2,2);
        Cell secondDestination = board.getCell(2,1);

        try {
            test.execute(player, firstDestination, 0);
            test.execute(player,secondDestination,0);
            assertFalse(firstDestination.equals(secondDestination));
        }
        catch(IllegalArgumentException e){
            assertTrue(firstDestination.equals(secondDestination));
        }
        catch(AthenaException e){ }
    }
}
