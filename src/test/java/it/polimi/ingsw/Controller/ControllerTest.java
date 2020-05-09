package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Divinities.Athena;
import it.polimi.ingsw.Model.Divinities.Atlas;
import org.junit.Test;

import static junit.framework.TestCase.*;


public class ControllerTest {

    Game game = new Game();
    Controller controller = game.getController();
    Player tester = new Player("tester", Color.Red, game);
    Player tester2 = new Player("tester2", Color.Red, game);

    @Test
    public void testDefaultInput() {
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Default);


        controller.update("tester placeWorker 0 0");

        // check if placeWorker function placed the worker right
        assertFalse(game.getBoard().getCell(0,0).getIsEmpty());

        controller.update("tester placeWorker 4 4");
        controller.update("tester placeWorker 1 0");

        // check if placeWorker function place 2 and only 2 worker
        assertEquals(2, tester.getWorkers().size());

        controller.update("tester2 placeWorker 3 4");
        controller.update("tester2 placeWorker 3 3");

        controller.update("tester usePower");
        controller.update("tester move 1 1 0");

        // check the move function
        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());


        controller.update("tester build 0 0 0");

        // check the build function
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());

    }


    @Test
    public void testGodPower() {
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Athena);
        tester.setGodPower(new Athena());
        tester2.setDivinity(Divinity.Atlas);
        tester2.setGodPower(new Atlas());

        controller.update("tester placeWorker 0 0");
        controller.update("tester placeWorker 4 4");

        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 placeWorker 4 3");

        controller.update("tester usePower");
        controller.update("tester superMove 1 1 0");

        // check the superMove function
        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());

        controller.update("tester build 0 0 0");

        controller.update("tester2 usePower");
        controller.update("tester2 move 3 2 0");
        controller.update("tester2 superBuild 3 3 0");

        // check the superBuild function
        assertTrue(game.getBoard().getCell(3,3).getLevel() > 0);

    }
}