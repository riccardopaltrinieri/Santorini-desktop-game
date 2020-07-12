package it.polimi.ingsw;

import it.polimi.ingsw.Network.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ServerApp {
    public static void main( String[] args ) {
        Server server;
        String confFilePath = "network.json";
        int port = 12445;
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(confFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            port = Integer.parseInt((String) jsonObject.get("port"));

        } catch (ParseException | IOException e) {
            System.err.println("Impossible to read the port. Use default!\n" + e.getMessage());
        }
        try{
            server = new Server(port);
            new Thread(server).start();
        }catch (IOException e) {
        System.err.println("Impossible to start the server!\n" + e.getMessage());
    }

    }
}
