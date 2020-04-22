package it.polimi.ingsw.Controller;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Divinities.Athena;
import it.polimi.ingsw.View.RemoteView;
import org.junit.Test;
import static junit.framework.TestCase.*;


public class ControllerTest {

    Game game = new Game();
    Board board = game.getBoard();
    Controller controller = game.getController();
    Player tester = new Player("tester", Color.Red, game);
    Player tester2 = new Player("tester2", Color.Red, game);
    RemoteView client = new RemoteView();

    @Test
    public void testGodPower() {
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Athena);
        tester.setGodPower(new Athena());

        client.addObserver(controller);
        tester2.placeWorkers(board.getCell(3,3));
        tester2.placeWorkers(board.getCell(2,2));

        client.notifyObservers("tester placeWorker 0 0");
        client.notifyObservers("tester placeWorker 4 4");
        client.notifyObservers("tester usePower -1 -1 -1");

        client.notifyObservers("tester superMove 1 1 0");

        // check the superMove function
        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());

        //check the (athena) super power
        assertFalse(game.getCanMoveUp());

    }

    @Test
    public void testDefaultInput() {
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Default);

        client.addObserver(controller);
        tester2.placeWorkers(board.getCell(3,3));
        tester2.placeWorkers(board.getCell(2,2));

        client.notifyObservers("tester placeWorker 0 0");

        // check if placeWorker function placed the worker right
        assertFalse(game.getBoard().getCell(0,0).getIsEmpty());

        client.notifyObservers("tester placeWorker 4 4");
        client.notifyObservers("tester placeWorker 1 0");

        // check if placeWorker function place 2 and only 2 worker
        assertEquals(2, tester.getWorkers().size());

        client.notifyObservers("tester usePower 0 0 0");
        client.notifyObservers("tester move 1 1 0");

        // check the move function
        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());


        client.notifyObservers("tester build 0 0 0");

        // check the build function
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());

    }

}