package it.polimi.ingsw.Model;

import it.polimi.ingsw.AthenaException;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class WorkerTest {
    int riga=0;
    int colonna=0;
    Cell workerPosition = new Cell(riga,colonna);
    Player workerOwner = new Player("tester", Color.Red, new Game());
    Worker test = new Worker(workerPosition,workerOwner);

    @Test
    public void testCostruttore(){
        assertSame(test.getPosition(), workerPosition);
        assertSame(test.getOwner(), workerOwner);
    }

    @Test
    public void testMove() throws AthenaException {
        Cell destination= new Cell(riga+1,colonna+1);
        try{
            test.move(destination);
            assertSame(test.getPosition(), destination);
            assertFalse(test.getPosition().getIsEmpty());
            assertTrue(workerPosition.getIsEmpty());
        }catch (IllegalArgumentException | AthenaException a){
            assertSame(test.getPosition(), workerPosition);
            assertTrue(test.getPosition().getIsEmpty());
            assertFalse(destination.getIsEmpty());
        }

    }

    @Test
    public void testBuild(){
        Cell destination = new Cell(colonna+1,riga+1);
        int startLevel = destination.getLevel();
        //boolean prova =test.getPosition().canBuildIn(destination);
        if(test.getPosition().canBuildIn(destination)){
            test.build(destination);
            assertEquals(test.getPosition().getLevel(), destination.getLevel() - 1);
            assertEquals(destination.getLevel(), startLevel + 1);
        }
        else{
            try{
                test.build(destination);
            }catch (IllegalArgumentException e) {
                assertEquals(test.getPosition().getLevel(), destination.getLevel());
                assertEquals(destination.getLevel(), startLevel);
            }
        }
    }
}
