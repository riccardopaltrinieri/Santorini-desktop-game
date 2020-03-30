package it.polimi.ingsw;

import it.polimi.ingsw.Model.Tab;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TabTest {

    Tab tab = new Tab();

    @Test
    public void TabellaTest() {
        assertEquals(25, tab.getNumColumn() * tab.getNumRow(), 0);
    }
}
