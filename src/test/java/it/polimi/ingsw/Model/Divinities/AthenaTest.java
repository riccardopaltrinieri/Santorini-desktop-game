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
        Board board = game.getBoard();
        Player player = new Player("test player", Color.Brown, game);

        Cell lowCell = board.getCell(0,0);
        Cell highCell = board.getCell(1, 1);
        highCell.setLevel(1);

        player.placeWorkers(board.getCell(0,0));
        try {
            //low to high -> canMoveUp = true
            test.execute(player, highCell, 0);
            assertTrue(game.getCanMoveUp());
            //high to low -> canMoveUp = false
            test.execute(player,lowCell,0);
            assertFalse(game.getCanMoveUp());

        } catch (IllegalArgumentException | AthenaException e) {
            System.out.println(e.getMessage());
        }
    }
}
