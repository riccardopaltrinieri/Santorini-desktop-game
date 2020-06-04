package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import junit.framework.TestCase;

public class DefaultDivinityTest extends TestCase {

    public void testExecute() throws AthenaException {
        Player tester = new Player("tester", Color.Brown, new Game());
        tester.setGodPower("default");
        tester.getGodPower().execute(tester, new Cell(1, 1),0);
    }

    public void testGetDivinity() {
        Player tester = new Player("tester", Color.Brown, new Game());
        tester.setGodPower("default");
        assertEquals(tester.getGodPower().getDivinity(), Divinity.Default);
    }
}