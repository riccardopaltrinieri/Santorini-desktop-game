package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;
import it.polimi.ingsw.View.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        final MainFrame GUI = new MainFrame();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.initGUI();
            }
        });
        CLInterface CLI = new CLInterface("127.0.0.1", 12345);
        try{
            CLI.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
