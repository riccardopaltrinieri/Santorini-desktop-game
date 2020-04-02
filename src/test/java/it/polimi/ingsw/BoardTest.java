package it.polimi.ingsw;

import it.polimi.ingsw.Model.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    Board board = new Board();





    @Test
    public void TabellaTest() {
        assertEquals(25, board.getNumColumn() * board.getNumRow(), 0);






    }
}
