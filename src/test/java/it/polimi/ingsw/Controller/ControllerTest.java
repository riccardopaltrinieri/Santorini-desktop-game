package it.polimi.ingsw.Controller;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.RemoteView;
import org.junit.Test;
import static junit.framework.TestCase.*;


public class ControllerTest {

    Game game = new Game();
    Controller controller = game.getController();
    Player tester = new Player("tester", Color.Red, game);
    Player tester2 = new Player("tester2", Color.Red, game);
    RemoteView client = new RemoteView();

    @Test
    public void testGodPower() throws AthenaException {
        //insert the power to test
        tester.setDivinity(Divinity.Default);

        game.addPlayer(tester);
        game.addPlayer(tester2);
        client.addObserver(controller);
        tester.placeWorkers(0,0);
        tester.placeWorkers(4,4);
        tester2.placeWorkers(3,3);
        tester2.placeWorkers(2,2);

        client.notifyObservers("usePower -1 -1 -1");
        client.notifyObservers("move 1 1 0");
        client.notifyObservers("build 0 0 0");

        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());

    }

    @Test
    public void testDefaultInput() throws AthenaException {
        //insert the power to test
        tester.setDivinity(Divinity.Default);

        game.addPlayer(tester);
        game.addPlayer(tester2);
        client.addObserver(controller);
        tester.placeWorkers(0,0);
        tester.placeWorkers(4,4);
        tester2.placeWorkers(3,3);
        tester2.placeWorkers(2,2);

        client.notifyObservers("default -1 -1 -1");
        client.notifyObservers("move 1 1 0");
        client.notifyObservers("build 0 0 0");

        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());

    }

}