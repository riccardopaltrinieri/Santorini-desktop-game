package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HephaestusTest {

    @Test
    public void executeTest() throws AthenaException {
        Game game = new Game();
        Board board = game.getBoard();
        Player player = new Player("test player", Color.Brown,game);
        player.setGodPower(new Hephaestus());
        assertEquals(Divinity.Hephaestus, player.getGodPower().getDivinity());

        player.placeWorkers(board.getCell(0,0));
        Cell rightDestination = board.getCell(1,1);
        int startingLevel = rightDestination.getLevel();
        Cell wrongDestination = board.getCell(1,0);
        int startingWrongLevel = 2;
        wrongDestination.setLevel(startingWrongLevel);

        player.getGodPower().execute(player,rightDestination,0);
        assertEquals(rightDestination.getLevel(), startingLevel + 2);
        try{
            player.getGodPower().execute(player,wrongDestination,0);
        }
        catch (IllegalArgumentException e){
            assertEquals(wrongDestination.getLevel(), startingWrongLevel);
        }
    }
}
