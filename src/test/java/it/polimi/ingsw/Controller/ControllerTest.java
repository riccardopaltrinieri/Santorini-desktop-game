package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Divinities.Athena;
import it.polimi.ingsw.Model.Divinities.Atlas;
import org.junit.Test;

import static junit.framework.TestCase.*;


public class ControllerTest {

    @Test
    public void testDefaultInput() {

        Game game = new Game();
        Controller controller = game.getController();
        Player tester = new Player("tester", Color.Red, game);
        Player tester2 = new Player("tester2", Color.Red, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Default);


        controller.update("tester placeWorker 1 1");

        // check if placeWorker function placed the worker right
        assertFalse(game.getBoard().getCell(0,0).getIsEmpty());

        controller.update("tester placeWorker 4 4");
        controller.update("tester placeWorker 1 2");

        // check if placeWorker function place 2 and only 2 worker
        assertEquals(2, tester.getWorkers().size());

        controller.update("tester2 placeWorker 3 4");
        controller.update("tester2 placeWorker 3 3");

        controller.update("tester normal");
        controller.update("tester move 1 2 1");

        // check the move function
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());


        controller.update("tester build 1 1 1");

        // check the build function
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());

    }


    @Test
    public void testGodPower() {

        Game game = new Game();
        Controller controller = game.getController();
        Player tester = new Player("tester", Color.Red, game);
        Player tester2 = new Player("tester2", Color.Red, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setDivinity(Divinity.Athena);
        tester.setGodPower(new Athena());
        tester2.setDivinity(Divinity.Atlas);
        tester2.setGodPower(new Atlas());

        controller.update("tester placeWorker 1 1");
        controller.update("tester placeWorker 4 4");

        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 placeWorker 4 3");

        controller.update("tester usePower");
        controller.update("tester superMove 1 2 1");

        // check the superMove function
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());

        controller.update("tester build 1 1 1");

        controller.update("tester2 usePower");
        controller.update("tester2 move 3 2 1");
        controller.update("tester2 superBuild 3 3 1");

        // check the superBuild function
        assertTrue(game.getBoard().getCell(2,2).getLevel() > 0);

    }
}