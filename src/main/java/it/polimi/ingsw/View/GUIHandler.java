package it.polimi.ingsw.View;

import java.util.Scanner;
import javax.swing.*;

public class GUIHandler implements UserInterface{

    MainFrame mainFrame;

    public GUIHandler(MainFrame mainFrame){
        this.mainFrame=mainFrame;
    }

    private String name;
    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        String outgoingMessage = "Error";

        try{
            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];

            switch (firstWord){
                case "Welcome!":
                    
                    //insert the player name
                    outgoingMessage = (String)JOptionPane.showInputDialog(
                            mainFrame,
                            "Insert your name here",
                            "Player's name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "");
                    while((outgoingMessage.equals(""))||(outgoingMessage==null)){
                        outgoingMessage = (String)JOptionPane.showInputDialog(
                                mainFrame,
                                "The given name is not valid, try again",
                                "Player's name",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");
                    }
                    break;
                case "Wait":

                    //waiting for the other players
                    mainFrame.setTextAreaString("Waiting for the other Players");
                    mainFrame.updateTextArea();
                    outgoingMessage="noMessageToSend";
                    break;
            }
            return outgoingMessage;
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
