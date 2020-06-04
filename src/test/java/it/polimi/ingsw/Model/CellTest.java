package it.polimi.ingsw.Model;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class CellTest {
    int x=0;
    int y=0;
    Cell test= new Cell(x,y);

     @Test
    public void testConstructor(){
         assertEquals (test.getNumColumn(),x);
         assertEquals (test.getNumRow(),y);
         assertTrue(test.getIsEmpty());
         assertEquals(0, test.getLevel());
         assertTrue(test.getNumRow()<5);
         assertTrue(test.getNumColumn()<5);
     }

     @Test(expected = IllegalArgumentException.class)
     public void testConstructorEx() {
         test = new Cell(1, 6);
         test = new Cell(-1, 1);
     }

     @Test
     public void testEquals() {
         Cell test1 = new Cell(1, 1);
         Cell test2 = new Cell(1, 1);
         Cell test3 = new Cell(1, 2);

         assertTrue(test1.equals(test2));
         assertFalse(test2.equals(test3));
     }
}
