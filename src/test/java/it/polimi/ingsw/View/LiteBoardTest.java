package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import it.polimi.ingsw.utils.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;


public class LiteBoardTest {


    Game game = new Game();
    Board tabella = game.getBoard();
    Player player1 = new Player("roni", Color.Red, game);
    Player player2 = new Player("rsani",Color.Purple, game);

    @Test
    public void testConstructor (){
        Cell test1 = tabella.getCell(1,1);
        Cell test2 = tabella.getCell(2,1);
        player1.placeWorkers(test1);
        player1.placeWorkers(test2);
        Cell test3 = tabella.getCell(3,2);
        Cell test4 = tabella.getCell(0,1);
        player2.placeWorkers(test3);
        player2.placeWorkers(test4);
        LiteBoard test;
        tabella.getCell(1,1).setLevel(1);
        test = new LiteBoard("ciao", tabella, game);
        test.printBoardCLI();

    }
    @Test
    public void TestConstructorMessage (){
        LiteBoard test2 = new LiteBoard("Check");
        assertEquals("Ok", "Check", test2.getMessage());
    }

    @Test
    public void testJson() {
        Cell test1 = tabella.getCell(1,1);
        Cell test2 = tabella.getCell(2,1);
        player1.placeWorkers(test1);
        player1.placeWorkers(test2);
        Cell test3 = tabella.getCell(3,2);
        Cell test4 = tabella.getCell(0,1);
        player2.placeWorkers(test3);
        player2.placeWorkers(test4);
        tabella.getCell(1,1).setLevel(1);

        LiteBoard board = new LiteBoard("ciao", tabella, game);
        System.out.println(board.toJson());

        try {
            JSONArray json = (JSONArray) new JSONParser().parse(board.toJson());
            System.out.println(json.get(0));
            System.out.println(json.get(1));
            System.out.println(json.get(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


