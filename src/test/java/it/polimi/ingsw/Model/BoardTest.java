package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is the test of board. We use statement coverage.
 */
public class BoardTest {

    Board board = new Board();
    int x;
    int y;
    Cell cellTest;



    @Test
    public void BoardCreationTest() {
        assertEquals(25, board.getNumColumn() * board.getNumRow(), 0);
        for (x = 0; x < 5; x++) {

            for (y = 0; y < 5; y++) {
                assertEquals(x, board.getMap()[x][y].getNumRow(), 0);
                assertEquals(y, board.getMap()[x][y].getNumColumn(), 0);
            }
        }
    }
    @Test
    public void clearCellTest() {
        cellTest = new Cell(0, 0);
        board.clearCell(cellTest);
        assertTrue(board.getMap()[0][0].getIsEmpty());
    }
    @Test
    public void clearAllTest(){
        board.clearAll();
        for (x=0;x<5;x++) {
            for (y = 0; y < 5; y++)
            {
                assertTrue(board.getMap()[x][y].getIsEmpty());
            }
        }

    }
}
