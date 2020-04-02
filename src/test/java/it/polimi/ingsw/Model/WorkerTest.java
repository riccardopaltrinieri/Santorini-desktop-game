package it.polimi.ingsw.Model;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class WorkerTest {
    int x,y;
    Cell workerPosition = new Cell(x,y);
    Player workerOwner = new Player();
    Worker test = new Worker(workerPosition,workerOwner);

    @Test
    public void testCostruttore(){
        assertTrue(test.getPosition()==workerPosition);
        assertTrue(test.getOwner()==workerOwner);
    }

    @Test
    public void testMove(){
        Cell destination= new Cell(x+1,y+1);
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
        Cell destination = new Cell(x+1,y+1);
        int startLevel = destination.getLevel();
        test.build(destination);
        if(test.getPosition().canBuildIn(destination)){
            assertTrue(test.getPosition().getLevel()==destination.getLevel()-1);
            assertTrue(destination.getLevel()==startLevel+1);
        }
        else{
            assertTrue(test.getPosition().getLevel()==destination.getLevel());
            assertTrue(destination.getLevel()==startLevel);
        }
    }
}
