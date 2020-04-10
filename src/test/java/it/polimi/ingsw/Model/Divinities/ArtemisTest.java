package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.GodPower;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArtemisTest {
    private GodPower test = new Artemis();

    @Test
    public void testCostruttore(){
        assertTrue(test.getDivinity()== Divinity.Artemis);
    }
}
