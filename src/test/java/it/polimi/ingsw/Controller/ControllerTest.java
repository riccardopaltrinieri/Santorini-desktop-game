package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Divinities.*;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.Color;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.*;


public class ControllerTest {

    @Test
    public void testDefaultInput() {

        Game game = new Game();
        Controller controller = new Controller(game);
        Player tester = new Player("tester", Color.White, game);
        Player tester2 = new Player("tester2", Color.White, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setGodPower("default");
        tester2.setGodPower("default");


        controller.update("tester placeWorker 1 1");

        // check if placeWorker function placed the worker right
        assertFalse(game.getBoard().getCell(0,0).getIsEmpty());

        controller.update("tester placeWorker 4 4");
        controller.update("tester placeWorker 1 2");
        controller.update("tester endturn");
        // check if placeWorker function place 2 and only 2 worker
        assertEquals(2, tester.getWorkers().size());

        controller.update("tester2 placeWorker 3 4");
        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 endturn");
        controller.update("tester normal");
        controller.update("tester move 1 2 1");

        // check the move function
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());


        controller.update("tester build 1 1 1");

        // check the build function
        assertFalse(game.getBoard().getCell(0,0).getLevel()==0);
        assertEquals(1, game.getBoard().getCell(0, 0).getLevel());
        controller.update("tester endturn");

        // Check the exception
        controller.update("tester normal");
    }


    @Test
    public void testGodPowerAthenaAtlas() {

        Game game = new Game();
        Controller controller = new Controller(game);
        Player tester = new Player("tester", Color.White, game);
        Player tester2 = new Player("tester2", Color.White, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setGodPower("Athena");
        tester2.setGodPower("Atlas");

        controller.update("tester placeWorker 1 1");
        controller.update("tester placeWorker 4 4");
        controller.update("tester endturn");
        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 placeWorker 4 3");
        controller.update("tester2 endturn");
        controller.update("tester usePower");
        controller.update("tester move 1 2 1");

        // check the superMove function
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());

        controller.update("tester build 1 1 1");
        controller.update("tester endturn");
        controller.update("tester2 usePower");
        controller.update("tester2 move 3 2 1");
        controller.update("tester2 build 3 3 1");

        // check the superBuild function
        assertTrue(game.getBoard().getCell(2,2).getLevel() > 0);

    }
    @Test
    public void testGodPowerPanPrometheus() {

        Game game = new Game();
        Controller controller = new Controller(game);
        Player tester = new Player("tester", Color.White, game);
        Player tester2 = new Player("tester2", Color.White, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setGodPower("Pan");
        tester.setGodPower(new Pan());
        Pan testPan = new Pan();
        assertEquals( testPan.getDivinity(), tester.getGodPower().getDivinity());
        tester2.setGodPower("Prometheus");
        Prometheus testProm = new Prometheus();
        assertEquals( testProm.getDivinity(), tester2.getGodPower().getDivinity());

        controller.update("tester placeWorker 1 1");
        controller.update("tester placeWorker 4 4");
        controller.update("tester endturn");
        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 placeWorker 4 3");
        controller.update("tester2 endturn");
        // Pan player makes a normal move with the GodPower
        controller.update("tester usePower");
        controller.update("tester move 1 2 1");
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());
        controller.update("tester build 1 1 1");
        controller.update("tester endturn");
        // Prometheus player use the GodPower
        controller.update("tester2 usePower");
        controller.update("tester2 build 3 2 1");
        controller.update("tester2 move 2 2 1");
        controller.update("tester2 build 3 3 1");
        assertEquals(game.getBoard().getCell(2, 1).getLevel(), 1);
        assertFalse(game.getBoard().getCell(1, 1).getIsEmpty());
        assertEquals(game.getBoard().getCell(2, 2).getLevel(), 1);
    }

    @Test
    public void testGodPowerArtemisDemeter() {
        Game game = new Game();
        Controller controller = new Controller(game);
        Player tester = new Player("tester", Color.White, game);
        Player tester2 = new Player("tester2", Color.White, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setGodPower("Artemis");
        tester2.setGodPower("Demeter");

        controller.update("tester placeWorker 1 1");
        controller.update("tester placeWorker 2 2");
        controller.update("tester endturn");
        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 placeWorker 4 4");
        controller.update("tester2 endturn");
        // Artemis player makes two moves
        controller.update("tester usePower");
        controller.update("tester move 1 2 1");
        controller.update("tester move 1 3 1");
        assertFalse(game.getBoard().getCell(0,2).getIsEmpty());
        assertTrue(game.getBoard().getCell(0,0).getIsEmpty());
        assertTrue(game.getBoard().getCell(0,1).getIsEmpty());
        controller.update("tester build 1 2 1");
        controller.update("tester endturn");
        // Demeter player use the GodPower
        controller.update("tester2 usePower");
        controller.update("tester2 move 3 4 1");
        controller.update("tester2 build 3 3 1");
        controller.update("tester2 build 3 3 1");
        controller.update("tester2 build 3 5 1");
        assertEquals(game.getBoard().getCell(2, 2).getLevel(), 1);
        assertEquals(game.getBoard().getCell(2, 4).getLevel(), 1);
    }

    @Test
    public void testNewBoard () {
        Controller controller = new Controller(new Game());
        controller.newBoard(new LiteBoard("foo"));
    }

    @Test
    public void testUndoHandler() throws InterruptedException {

        Game game = new Game();
        Controller controller = new Controller(game);
        Player tester = new Player("tester", Color.White, game);
        Player tester2 = new Player("tester2", Color.White, game);
        game.setNumPlayer(2);
        //insert the power to test
        tester.setGodPower("default");
        tester2.setGodPower("default");


        controller.update("tester placeWorker 1 1");
        controller.update("tester placeWorker 4 4");
        controller.update("tester endturn");

        controller.update("tester2 placeWorker 3 4");
        controller.update("tester2 placeWorker 3 3");
        controller.update("tester2 endturn");
        controller.update("tester normal");

        // Save the state, do the action and restore the state
        //UndoHandler undoThread1 = new UndoHandler(game, controller, controller.getFsm().getState());
        controller.update("tester move 1 2 1");
        //new Thread(undoThread1).start();
        controller.update("tester undo");

        // Check if the undo function has worked
        sleep(5500);
        assertTrue(game.getBoard().getCell(0,1).getIsEmpty());

        controller.update("tester move 1 2 1");
        sleep(5500);
        controller.update("tester undo");

        //new Thread(new UndoHandler(game, controller, State.undo)).start();
        sleep(6500);
        // Check if the undo function hasn't worked
        assertFalse(game.getBoard().getCell(0,1).getIsEmpty());
    }
}