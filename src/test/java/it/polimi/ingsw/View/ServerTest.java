package it.polimi.ingsw.View;

import junit.framework.TestCase;
import org.junit.Test;
import static junit.framework.TestCase.*;

import java.io.IOException;
import java.net.Socket;

public class ServerTest {
    Server server = new Server();

    public ServerTest() throws IOException {
    }




    @Test
    public void testEndGame() {
        server.endGame();
        assertEquals(0,server.dimConnections());
    }

}