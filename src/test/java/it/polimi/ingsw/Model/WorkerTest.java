package it.polimi.ingsw.Model;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class WorkerTest {
    int riga=0;
    int colonna=0;
    Cell workerPosition = new Cell(riga,colonna);
    Player workerOwner = new Player();
    Worker test = new Worker(workerPosition,workerOwner);

    @Test
    public void testCostruttore(){
        assertTrue(test.getPosition()==workerPosition);
        assertTrue(test.getOwner()==workerOwner);
    }

    @Test
    public void testMove(){
        Cell destination= new Cell(riga+1,colonna+1);
        test.move(destination);
        if (test.getPosition().canMoveTo(destination)) {
            assertTrue(test.getPosition() == destination);
        }
        else
        {
            assertTrue(test.getPosition()==workerPosition);
        }
    }

    @Test
    public void testBuild(){
        Cell destination = new Cell(colonna+1,riga+1);
        int startLevel = destination.getLevel();
        boolean prova =test.getPosition().canBuildIn(destination);
        if(test.getPosition().canBuildIn(destination)){
            test.build(destination);
            assertTrue(test.getPosition().getLevel()==destination.getLevel()-1);
            assertTrue(destination.getLevel()==startLevel+1);
        }
        else{
            try{
                test.build(destination);
            }catch (IllegalArgumentException e) {
                assertTrue(test.getPosition().getLevel() == destination.getLevel());
                assertTrue(destination.getLevel() == startLevel);
            }
        }
    }
}
