package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtlasTest {

    @Test(expected = IllegalArgumentException.class)
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Board board = game.getBoard();
        Player player = new Player("test player", Color.Red,game);
        player.setGodPower(new Atlas());
        assertEquals(Divinity.Atlas, player.getGodPower().getDivinity());

        player.placeWorkers(board.getCell(1,1));
        Cell destinationLevel0 = board.getCell(0,0);
        Cell destinationLevel1 = board.getCell(0,1);
        Cell destinationLevel2 = board.getCell(1,0);
        destinationLevel2.setLevel(2);
        Cell destinationLevel3 = board.getCell(2,2);
        destinationLevel3.setLevel(3);
        Cell destinationLevel4 = board.getCell(2,1);
        destinationLevel3.setLevel(4);

        player.getGodPower().execute(player,destinationLevel0,0);
        player.getGodPower().execute(player,destinationLevel1,0);
        player.getGodPower().execute(player,destinationLevel2,0);
        player.getGodPower().execute(player,destinationLevel3,0);

        assertEquals(destinationLevel0.getLevel(),destinationLevel1.getLevel());
        assertEquals(destinationLevel1.getLevel(),destinationLevel2.getLevel());
        assertEquals(destinationLevel2.getLevel(),destinationLevel3.getLevel());
        assertEquals(destinationLevel3.getLevel(),4);

        // Wrong ones (expected exception)
        player.getGodPower().execute(player,destinationLevel4,0);
        player.getGodPower().execute(player,board.getCell(4, 4),0);
    }
}
