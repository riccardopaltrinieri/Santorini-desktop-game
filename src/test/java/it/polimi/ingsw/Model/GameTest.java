package it.polimi.ingsw.Model;

import it.polimi.ingsw.utils.Color;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */


public class GameTest
{
    Game game = new Game();
    Player tester = new Player("tester", Color.White, game);
    Player tester2 = new Player("tester2", Color.Red, game);
    Player tester3 = new Player("tester2", Color.Purple, game);

    @Test
    public void testPlaceWorker() {

        // True if the worker has been placed, false otherwise
        assertTrue(game.placeWorker(1, 1));
        assertFalse(game.placeWorker(1, 1));
    }

    @Test
    public void testMove() {

        // The first player is tester
        game.placeWorker(1, 1);
        // The first move is legitimate
        assertTrue(game.move(1, 2, 0));
        // Cannot move in the same Cell
        assertFalse(game.move(1, 2, 0));

        // Change player to tester2
        game.setNumPlayer(2);
        game.endTurn();
        game.placeWorker(2, 2);

        // Cannot move in the same cell of the first worker
        assertFalse(game.move(1, 2, 0));

        // Simulate the Athena godPower
        game.setCanMoveUp(false);
        game.getBoard().getCell(3, 3).setLevel(1);
        // Cannot move up because the Athena power is active
        assertFalse(game.move(3, 3, 0));

        // Simulate the block of a worker
        Cell pos = game.getBoard().getCell(2, 2);
        // Fill all the cells from the one top-left to the one down-right
        for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
            for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                if(0 <= row && row < 5 && 0 <= col && col < 5) game.getBoard().getCell(row, col).setEmpty(false);

        assertFalse(game.move(2, 3, 0));
    }

    @Test
    public void testBuild() {

        // The first player is tester
        game.placeWorker(1, 1);
        // The first build is legitimate
        assertTrue(game.build(1, 2, 0));
        // Cannot build 2 cells away
        assertFalse(game.build(3, 3, 0));
    }

    @Test
    public void testUseGodPower() {

        tester.setGodPower("Athena");
        tester2.setGodPower("Artemis");
        // The first player is tester
        game.placeWorker(1, 1);
        // The first move is legitimate
        assertTrue(game.useGodPower(1, 2, 0));

        // Change player to tester2
        game.setNumPlayer(2);
        game.endTurn();

        game.placeWorker(2, 2);

        // Cannot move in the same cell of the first worker
        assertFalse(game.useGodPower(1, 2, 0));

        // Cannot move up because the Athena power is active
        game.getBoard().getCell(3, 3).setLevel(1);
        assertFalse(game.move(3, 3, 0));
        assertFalse(game.useGodPower(3, 3, 0));

        // Simulate the block of a worker
        Cell pos = game.getBoard().getCell(2, 2);
        // Fill all the cells from the one top-left to the one down-right
        for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
            for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                if(0 <= row && row < 5 && 0 <= col && col < 5) game.getBoard().getCell(row, col).setEmpty(false);

        assertFalse(game.useGodPower(2, 3, 0));
    }

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
        game.hasLoser();
        assertTrue(cell.getIsEmpty());
        assertTrue(cell2.getIsEmpty());
        assertFalse(cell3.getIsEmpty());
    }
}
