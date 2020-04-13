package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class MinotaurTest {
    GodPower test = new Minotaur();

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Player player=new Player("test player", Color.Yellow,game);
        Board board = new Board();
        game.setBoard(board);
        Player foo = new Player("enemy foo test", Color.Green,game);
        player.placeWorkers(board.getCell(0,0));
        player.placeWorkers(board.getCell(4,4));
        foo.placeWorkers(board.getCell(1,1));
        foo.placeWorkers(board.getCell(3,2));
        test.execute(player,foo.getWorker(0).getPosition(),0);
        assertTrue(foo.getWorker(0).getPosition().equals(board.getCell(2,2)));
        assertTrue(player.getWorker(0).getPosition().equals(board.getCell(1,1)));
    }
}
