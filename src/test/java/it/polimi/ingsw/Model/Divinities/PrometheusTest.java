package it.polimi.ingsw.Model.Divinities;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest {

    @Test(expected = IllegalArgumentException.class)
    public void executeTest() {

        Game game = new Game();
        Player player = new Player("test player", Color.Purple,game);
        Board board = game.getBoard();
        player.setGodPower(new Prometheus());
        assertEquals(Divinity.Prometheus, player.getGodPower().getDivinity());

        try {
            game.setCanMoveUp(true);
            player.placeWorkers(board.getCell(0, 0));
            player.getGodPower().execute(player, board.getCell(1 ,1), 0);

            // Wrong one
            game.setCanMoveUp(false);
            board.getCell(2,2).setLevel(1);
            player.getGodPower().execute(player, board.getCell(2, 2), 0);
        } catch (AthenaException e) {
            e.printStackTrace();
        }

    }

}