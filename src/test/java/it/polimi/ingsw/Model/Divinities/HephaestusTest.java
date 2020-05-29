package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class HephaestusTest {
    GodPower test = new Hephaestus();

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Board board = game.getBoard();
        Player player = new Player("test player", Color.Brown,game);
        player.placeWorkers(board.getCell(0,0));
        Cell rightDestination = board.getCell(1,1);
        int startingLevel = rightDestination.getLevel();
        Cell wrongDestination = board.getCell(1,0);
        int startingWrongLevel = 2;
        wrongDestination.setLevel(startingWrongLevel);

        test.execute(player,rightDestination,0);
        assertTrue(rightDestination.getLevel()==startingLevel+2);
        try{
            test.execute(player,wrongDestination,0);
        }
        catch (IllegalArgumentException e){
            assertTrue(wrongDestination.getLevel()==startingWrongLevel);
        }
    }
}
