package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.utils.Color;
import org.junit.Test;

public class PanTest {
    GodPower test = new Pan();

    @Test
    public void panTest()
    {
        Game game = new Game();
        Player panPlayer = new Player("pan's player", Color.Brown, game);
        Board board = game.getBoard();
        panPlayer.placeWorkers(board.getCell(1,1));
        board.getCell(1,1).setLevel(2);
        panPlayer.setGodPower(test);
        try{
            panPlayer.getWorker(0).move(board.getCell(2,2));
        }
        catch (AthenaException e){
            System.out.println(e.getMessage());
        }
    }
}
