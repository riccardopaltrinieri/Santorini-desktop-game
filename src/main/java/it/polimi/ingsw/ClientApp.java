package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;
import it.polimi.ingsw.View.GUIHandler;
import it.polimi.ingsw.View.NetworkHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ClientApp {

    public static void main(String[] args){
        String confFilePath = "network.json";
        String ip = "127.0.0.1";
        int port = 12445;

        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(confFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            String port1 = (String) jsonObject.get("port");
            port = Integer.parseInt(port1);
            ip = (String) jsonObject.get("ip");

        } catch (ParseException | IOException e) {
            System.err.println("Impossible read the ip or the port!\n" + e.getMessage());
        }
        NetworkHandler connection = new NetworkHandler(ip, port);

        if(args.length != 0) {
            if(args[0].equals("-cli")) {
                try {
                    CLInterface CLI = new CLInterface();
                    connection.setUserInterface(CLI);
                    connection.run();

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("You can use the parameter -cli to use the command line interface");
                System.out.println("or none parameter to start the graphic interface");
            }
        } else {

                try {
                    GUIHandler GUI = new GUIHandler();
                    connection.setUserInterface(GUI);
                    connection.run();
                }
                catch (IOException e){
                    System.err.println(e.getMessage());
                }
            }

        }



    }
