package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ApolloTest {
    GodPower test = new Apollo();

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Player playerApollo = new Player("player test", Color.Green, game);
        Player player = new Player("player test",Color.Red, game);
        playerApollo.placeWorkers(0,0);
        playerApollo.placeWorkers(4,4);
        Cell oldApolloPosition = playerApollo.getWorker(0).getPosition();
        player.placeWorkers(1,1);
        player.placeWorkers(3,3);
        Cell oldPlayerPosition = player.getWorker(0).getPosition();
        test.execute(playerApollo,oldPlayerPosition,0);
        assertTrue(playerApollo.getWorker(0).getPosition().equals(oldPlayerPosition));
        assertTrue(player.getWorker(0).getPosition().equals(oldApolloPosition));
    }
}
