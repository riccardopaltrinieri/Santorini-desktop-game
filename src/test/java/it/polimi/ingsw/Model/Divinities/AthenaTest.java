package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AthenaTest {
    private GodPower test = new Athena();

    @Test
    public void executeTest() {
        Game game = new Game();
        Board board = new Board();
        Player player = new Player("test player", Color.Green, game);
        player.placeWorkers(board.getCell(0,0));
        Cell higCell = board.getCell(1, 1);
        higCell.setLevel(1);
        Cell lowCell = board.getCell(1, 1);
        try {
            test.execute(player, lowCell, 0);
            assertTrue(game.getCanMoveUp());
            test.execute(player,lowCell,0);
            assertFalse(game.getCanMoveUp());
        } catch (IllegalArgumentException | AthenaException e) {
            System.out.println(e.getMessage());
        }
    }
}
