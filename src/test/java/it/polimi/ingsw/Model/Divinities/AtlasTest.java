package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtlasTest {
    private GodPower test = new Atlas();

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Player player = new Player("test player", Color.Green,game);
        player.placeWorkers(0,0);
        Cell destinationLevel0 = new Cell(1,1);
        Cell destinationLevel1 = new Cell(1,1);
        destinationLevel1.setLevel(1);
        Cell destinationLevel2 = new Cell(1,1);
        destinationLevel2.setLevel(2);
        Cell destinationLevel3 = new Cell(1,1);
        destinationLevel3.setLevel(3);
        test.execute(player,destinationLevel0,0);
        test.execute(player,destinationLevel1,0);
        test.execute(player,destinationLevel2,0);
        test.execute(player,destinationLevel3,0);
        assertEquals(destinationLevel0.getLevel(),destinationLevel1.getLevel());
        assertEquals(destinationLevel1.getLevel(),destinationLevel2.getLevel());
        assertEquals(destinationLevel2.getLevel(),destinationLevel3.getLevel());
        assertEquals(destinationLevel3.getLevel(),4);
    }
}
