package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    Board board = new Board();
    int i;
    int j;



    @Test
    public void TabellaTest() {
        assertEquals(25, board.getNumColumn() * board.getNumRow(), 0);
        for (i=0;i<5;i++) {
            for (j = 0; j < 5; j++)
            {
            assertEquals(i, board.map[i][j].getNumRow(), 0);
                assertEquals(j, board.map[i][j].getNumColumn(), 0);
            }

        }




    }
}
