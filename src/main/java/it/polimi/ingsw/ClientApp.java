package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;
import it.polimi.ingsw.View.MainFrame;
import it.polimi.ingsw.View.NetworkHandler;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args){
        NetworkHandler connection = new NetworkHandler("127.0.0.1", 12345);
        Scanner stdin = new Scanner(System.in);

        System.out.println("Do you want to use the Graphics Interface or continue on the command line?");
        System.out.println("(write gui/cli): ");
        String input = stdin.nextLine();

        while (!input.equals("cli") && !input.equals("gui")) {
            System.out.println("please, write 'gui' or 'cli':");
            input = stdin.nextLine();
        }

        if(input.equals("cli")){
            try{
                CLInterface CLI = new CLInterface();
                connection.setUserInterface(CLI);
                connection.run();

            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        } else {

            final MainFrame GUI = new MainFrame();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GUI.initGUI();
                }
            });

        }



    }
}
