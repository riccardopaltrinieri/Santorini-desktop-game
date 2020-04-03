package it.polimi.ingsw.Model;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class PlayerTest {

    Board board = new Board();
    Game game = new Game(board);
    Player tester = new Player("Tester", Color.Red, game);

    @Test
    public void testPlaceWorkers() {
        tester.placeWorkers(1,1, 0);
        tester.placeWorkers(4,4,1);
        assertFalse(game.getBoard().getCell(1,1).isEmpty());
    }

    @Test
    public void canMoveTest() {
        tester.placeWorkers(1,1,0);
        tester.placeWorkers(2,2,1);
        assertTrue(tester.canMove());

        Player foo1 = new Player("foo", Color.Green, game);
        Player foo2 = new Player("foo", Color.Green, game);
        Player foo3 = new Player("foo", Color.Green, game);
        Player foo4 = new Player("foo", Color.Green, game);
        foo1.placeWorkers(0,0,0);
        foo1.placeWorkers(1,0,1);
        foo2.placeWorkers(0,1,0);
        foo2.placeWorkers(2,0,1);
        foo3.placeWorkers(0,2,0);
        foo3.placeWorkers(1,2,1);
        foo4.placeWorkers(2,1,0);
        /*Worker worker0 = tester.getWorker(0);
        for (int row = worker0.getPosition().getNumRow() - 1; row <= worker0.getPosition().getNumRow() + 1; row++)
            for (int col = worker0.getPosition().getNumColumn() - 1; col <= worker0.getPosition().getNumColumn() + 1; col++)
                board.getCell(row,col).setEmpty(false);

        Worker worker1 = tester.getWorker(1);
        for (int row = worker1.getPosition().getNumRow() - 1; row <= worker1.getPosition().getNumRow() + 1; row++)
            for (int col = worker1.getPosition().getNumColumn() - 1; col <= worker1.getPosition().getNumColumn() + 1; col++)
                board.getCell(row,col).setEmpty(false);*/

        assertFalse(tester.canMove());
    }




}