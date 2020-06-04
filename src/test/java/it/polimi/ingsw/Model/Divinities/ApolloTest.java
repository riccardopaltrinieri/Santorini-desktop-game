package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApolloTest {

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Player playerApollo = new Player("player test", Color.Brown, game);
        Player player = new Player("player test",Color.White, game);
        playerApollo.setGodPower(new Apollo());
        assertEquals(Divinity.Apollo, playerApollo.getGodPower().getDivinity());
        Board board = game.getBoard();

        playerApollo.placeWorkers(board.getCell(0,0));
        playerApollo.placeWorkers(board.getCell(4,4));
        Cell oldApolloPosition = playerApollo.getWorker(0).getPosition();

        player.placeWorkers(board.getCell(1,1));
        player.placeWorkers(board.getCell(2,2));
        Cell oldPlayerPosition = player.getWorker(0).getPosition();

        playerApollo.getGodPower().execute(playerApollo,oldPlayerPosition,0);
        assertTrue(playerApollo.getWorker(0).getPosition().equals(oldPlayerPosition));
        assertTrue(player.getWorker(0).getPosition().equals(oldApolloPosition));

        // Normal move
        playerApollo.getGodPower().execute(playerApollo, board.getCell(1, 2), 0);
        assertTrue(playerApollo.getWorker(0).getPosition().equals(board.getCell(1,2)));
    }
}
