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
         assertTrue(test.isEmpty());
         assertTrue(test.getLevel()==0);
         assertTrue(test.getNumRow()<5);
         assertTrue(test.getNumColumn()<5);
     }
     @Test
    public void testCanMoveTo(){
         Cell destination= new Cell(1,1);
         assertTrue(test.canMoveTo(destination));
         Cell wrongDestination = new Cell(3,3);
         assertFalse(test.canMoveTo(wrongDestination));
     }
     @Test
    public  void testCanBuildIn(){
         Cell destination= new Cell(1,1);
         assertTrue(test.canBuildIn(destination));
         Cell wrongDestination = new Cell(3,3);
         assertFalse(test.canBuildIn(wrongDestination));
     }
}
