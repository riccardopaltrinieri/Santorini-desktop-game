package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PanTest {

    @Test(expected = IllegalArgumentException.class)
    public void panTest()
    {
        Game game = new Game();
        Player panPlayer = new Player("pan's player", Color.Red, game);
        Board board = game.getBoard();
        panPlayer.setGodPower(new Pan());
        assertEquals(Divinity.Pan, panPlayer.getGodPower().getDivinity());

        panPlayer.placeWorkers(board.getCell(1,1));
        panPlayer.placeWorkers(board.getCell(4,4));
        panPlayer.getWorker(0).getPosition().setLevel(3);
        try{
            panPlayer.getGodPower().execute(panPlayer, board.getCell(2,2), 0);
            panPlayer.getGodPower().execute(panPlayer, board.getCell(3,3), 1);

            // Wrong one
            panPlayer.getGodPower().execute(panPlayer, board.getCell(0,0), 1);
        }
        catch (AthenaException e){
            System.out.println(e.getMessage());
        }
    }
}
