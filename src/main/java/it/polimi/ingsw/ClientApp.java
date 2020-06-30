package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;
import it.polimi.ingsw.View.GUIHandler;
import it.polimi.ingsw.View.NetworkHandler;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args){
        NetworkHandler connection = new NetworkHandler("127.0.0.1", 12445);
      //  Scanner stdin = new Scanner(System.in);

       // System.out.println("Do you want to use the Graphics Interface or continue on the command line?");
      //  System.out.println("(write gui/cli): ");
        String input = args[0];

       // while (!input.equals("cli") && !input.equals("gui")) {
        //    System.out.println("please, write 'gui' or 'cli':");
        //  input = args ;
      //  }

        if(input.equals("cli")){
            try{
                CLInterface CLI = new CLInterface();
                connection.setUserInterface(CLI);
                connection.run();

            }catch (IOException e){
                System.err.println(e.getMessage());
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
