package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;
import it.polimi.ingsw.View.GUIHandler;
import it.polimi.ingsw.View.NetworkHandler;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args){
        NetworkHandler connection = new NetworkHandler("127.0.0.1", 12445);

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
