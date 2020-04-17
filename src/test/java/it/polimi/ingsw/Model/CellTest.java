package it.polimi.ingsw.Model;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class CellTest {
    int x=0;
    int y=0;
    Cell test= new Cell(x,y);
     @Test
    public void testCostruttore(){
         assertEquals (test.getNumColumn(),x);
         assertEquals (test.getNumRow(),y);
         assertTrue(test.getIsEmpty());
         assertEquals(0, test.getLevel());
         assertTrue(test.getNumRow()<5);
         assertTrue(test.getNumColumn()<5);
     }
}
