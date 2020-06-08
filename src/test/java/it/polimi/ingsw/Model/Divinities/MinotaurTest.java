package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinotaurTest {

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Player player = new Player("test player", Color.Purple,game);
        Board board = game.getBoard();
        Player foo = new Player("enemy foo test", Color.Brown,game);
        player.setGodPower(new Minotaur());
        assertEquals(Divinity.Minotaur, player.getGodPower().getDivinity());

        player.placeWorkers(board.getCell(0,0));
        player.placeWorkers(board.getCell(4,4));
        foo.placeWorkers(board.getCell(1,1));
        foo.placeWorkers(board.getCell(3,2));
        player.getGodPower().execute(player,foo.getWorker(0).getPosition(),0);
        assertTrue(foo.getWorker(0).getPosition().equals(board.getCell(2,2)));
        assertTrue(player.getWorker(0).getPosition().equals(board.getCell(1,1)));

        player.getGodPower().execute(player, board.getCell(1, 2), 0);
    }
}
