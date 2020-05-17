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
                    mainFrame.updateTextArea("Insert your name in the dialog");
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
                    mainFrame.updateTextArea("Waiting for the other Players");
                    outgoingMessage="noMessageToSend";
                    break;
                case "Decide":
                    // Ask the number of players
                    mainFrame.updateTextArea("insert the player's number in the dialog");
                    String[] options = {"Two Players!", "Three Players!"};
                    int result = JOptionPane.showOptionDialog(
                            mainFrame,
                            "Decide the number of player",
                            "Player's number",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,     //no custom icon
                            options,  //button titles
                            options[0] //default button
                    );
                    if(result == JOptionPane.YES_OPTION){
                        outgoingMessage="2";
                    }else if (result == JOptionPane.NO_OPTION){
                        outgoingMessage="3";
                    }else {
                        while((outgoingMessage.equals("2")||(outgoingMessage.equals("3")))){
                            result = JOptionPane.showOptionDialog(
                                    mainFrame,
                                    "Decide the number of player",
                                    "Player's number",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //no custom icon
                                    options,  //button titles
                                    options[0] //default button
                            );
                            if(result == JOptionPane.YES_OPTION){
                                outgoingMessage="2";
                            }else if (result == JOptionPane.NO_OPTION) {
                                outgoingMessage = "3";
                            }
                        }
                    }

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
