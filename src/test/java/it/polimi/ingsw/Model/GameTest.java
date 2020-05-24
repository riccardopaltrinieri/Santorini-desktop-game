package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.Controller;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */


public class GameTest
{
    Game game = new Game();
    Controller controller = new Controller(game);
    Player tester = new Player("tester", Color.Red, game);
    Player tester2 = new Player("tester2", Color.Green, game);
    Player tester3 = new Player("tester2", Color.Yellow, game);

    //insert the power to test

    @Test
    public void TestHasLoser() {
        game.setNumPlayer(2);
        Cell cell = game.getBoard().getCell(2,3);
        tester.placeWorkers(cell);
        Cell cell2 = game.getBoard().getCell(4,3);
        tester2.placeWorkers(cell2);

        game.hasLoser();
        for (Cell[] c: game.getBoard().getMap()) {
                for (Cell c1:c) {
                    assertTrue(c1.getIsEmpty());
                }
            }
    }
    @Test
    public void TestHasLoser3Players() {

        game.setNumPlayer(3);
        Cell cell = game.getBoard().getCell(2,3);
        tester.placeWorkers(cell);
        Cell cell2 = game.getBoard().getCell(4,3);
        tester.placeWorkers(cell2);
        Cell cell3 = game.getBoard().getCell(2,4);
        tester3.placeWorkers(cell3);
        game.hasLoser(); //il primo deve sparire
        assertTrue(cell.getIsEmpty());
        assertTrue(cell2.getIsEmpty());
        assertFalse(cell3.getIsEmpty());
    }
}
