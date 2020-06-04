package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Divinities.*;
import it.polimi.ingsw.utils.Color;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class PlayerTest {

    Game game = new Game();
    Player tester = new Player("Tester", Color.White, game);
    Board board = game.getBoard();

    @Test
    public void testPlaceWorkers() {
        tester.placeWorkers(board.getCell(1,1));
        tester.placeWorkers(board.getCell(4,4));
        assertFalse(game.getBoard().getCell(1,1).getIsEmpty());
    }

    @Test
    public void setGodPowerString (){
        tester.setGodPower("apollo");
        assertTrue(tester.getGodPower() instanceof Apollo);
        tester.setGodPower("artemis");
        assertTrue(tester.getGodPower() instanceof Artemis);
        tester.setGodPower("athena");
        assertTrue(tester.getGodPower() instanceof Athena);
        tester.setGodPower("atlas");
        assertTrue(tester.getGodPower() instanceof Atlas);
        tester.setGodPower("demeter");
        assertTrue(tester.getGodPower() instanceof Demeter);
        tester.setGodPower("hephaestus");
        assertTrue(tester.getGodPower() instanceof Hephaestus);
        tester.setGodPower("minotaur");
        assertTrue(tester.getGodPower() instanceof Minotaur);
        tester.setGodPower("pan");
        assertTrue(tester.getGodPower() instanceof Pan);
        tester.setGodPower("prometheus");
        assertTrue(tester.getGodPower() instanceof Prometheus);
        tester.setGodPower("default");
        assertTrue(tester.getGodPower() instanceof DefaultDivinity);
    }

    @Test
    public void canMoveTest() {
        tester.placeWorkers(board.getCell(0,0));
        tester.placeWorkers(board.getCell(1,1));
        assertTrue(tester.canMove());

        Player foo1 = new Player("foo", Color.Brown, game);
        Player foo2 = new Player("foo", Color.Brown, game);
        Player foo3 = new Player("foo", Color.Brown, game);
        Player foo4 = new Player("foo", Color.Brown, game);
        foo1.placeWorkers(board.getCell(1,0));
        foo2.placeWorkers(board.getCell(0,1));
        foo2.placeWorkers(board.getCell(2,0));
        foo3.placeWorkers(board.getCell(0,2));
        foo3.placeWorkers(board.getCell(1,2));
        foo4.placeWorkers(board.getCell(2,1));
        foo4.placeWorkers(board.getCell(2,2));
        /*Worker worker0 = tester.getWorker(0);
        for (int row = worker0.getPosition().getNumRow() - 1; row <= worker0.getPosition().getNumRow() + 1; row++)
            for (int col = worker0.getPosition().getNumColumn() - 1; col <= worker0.getPosition().getNumColumn() + 1; col++)
                game.getBoard().getCell(row,col).setEmpty(false);

        Worker worker1 = tester.getWorker(1);
        for (int row = worker1.getPosition().getNumRow() - 1; row <= worker1.getPosition().getNumRow() + 1; row++)
            for (int col = worker1.getPosition().getNumColumn() - 1; col <= worker1.getPosition().getNumColumn() + 1; col++)
                game.getBoard().getCell(row,col).setEmpty(false);*/

        assertFalse(tester.canMove());
    }




}