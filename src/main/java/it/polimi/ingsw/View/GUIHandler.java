package it.polimi.ingsw.View;

import it.polimi.ingsw.View.Graphics.ChooseBetweenFrame;
import it.polimi.ingsw.View.Graphics.ChooseFrame;
import it.polimi.ingsw.View.Graphics.MainFrame;

import javax.swing.*;

public class GUIHandler implements UserInterface {

    MainFrame mainFrame= new MainFrame();
    ChooseFrame chooseFrame = new ChooseFrame();
    ChooseBetweenFrame chooseBetweenFrame = new ChooseBetweenFrame();
    private final FSMView fsm = new FSMView();

    private String name;
    private String firstGodToRemove;
    private String secondGodToRemove;
    private int numPlayer;

    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        String outgoingMessage = "Error";

        try{
            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];
            mainFrame.updateTextArea(incomingMessage);
            switch (firstWord){


                case "Welcome!":
                    //Ask gor the player's name
                    //TODO gestisci bottone cancel o toglilo
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public synchronized void  run() {
                            mainFrame.init();
                        }
                    });


                    mainFrame.updateTextArea("Insert your name in the dialog");
                    outgoingMessage = (String)JOptionPane.showInputDialog(
                            mainFrame,
                            "Insert your name here",
                            "Player's name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "");
                    while((outgoingMessage==null)||(outgoingMessage.equals(""))){
                        outgoingMessage = (String)JOptionPane.showInputDialog(
                                mainFrame,
                                "The given name is not valid, try again",
                                "Player's name",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");
                    }
                    name = outgoingMessage;
                    break;


                case "Wait":

                    //waiting for the other players
                    outgoingMessage="noMessageToSend";
                    break;


                case "Decide":
                    // Ask the number of players
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
                        numPlayer=2;
                    }else if (result == JOptionPane.NO_OPTION){
                        outgoingMessage="3";
                        numPlayer=3;
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
                                numPlayer=2;
                            }else if (result == JOptionPane.NO_OPTION) {
                                outgoingMessage = "3";
                                numPlayer=3;
                            }
                        }
                    }
                    break;


                case "Choose":
                    //ask the player to choose the divinity
                    if (parts[1].equals("the")) {
                        if ((parts[2].equals("second")) || (parts[2].equals("third"))) {
                            chooseFrame = new ChooseFrame();
                            chooseFrame.removeDivinityString(firstGodToRemove);
                            if (parts[2].equals("third")) {
                                chooseFrame.removeDivinityString(secondGodToRemove);
                            }
                        }
                        // Ask the name of a divinity or the number of players
                        chooseFrame.setDivinityNumber(parts[2]);
                        chooseFrame.init();
                        //TODO gestisci caso chiusura finestra

                        outgoingMessage = chooseFrame.getChosenDivinity();
                        if (parts[2].equals("first")) {
                            firstGodToRemove = outgoingMessage;
                        } else if (parts[2].equals("second")) {
                            secondGodToRemove = outgoingMessage;
                        }
                    }
                    else if (parts[1].equals("your")){
                        if (parts.length==6){
                            chooseBetweenFrame.init(parts[4],parts[5]);
                        }
                        else{
                            chooseBetweenFrame.init(parts[4],parts[5],parts[6]);
                        }
                        outgoingMessage=chooseBetweenFrame.getChosenDivinity();
                    }

                    break;


                case "Your":
                    // Shows to the player his God
                    mainFrame.updateGodCard(parts[2]);
                    outgoingMessage = "noMessageToSend";
                    break;


                case "insert":
                    //ask to the player for the next move according to the FSM
                    if (parts[1].equals(name)){
                        if (fsm.getState()==State.placeworker){
                            int row=1;
                            int column=0;
                            for (int i=0; i<25;i++){
                                if (column <5){
                                    column ++;
                                }
                                else{
                                    row++;
                                    column=1;
                                }
                                for (int j=0;j<numPlayer*2;j++){
                                    if((board.getPosWorker()[j][0]==row)&&(board.getPosWorker()[j][1]==column)){
                                        mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                                    }
                                    else {
                                        mainFrame.getActiveBoardButtons()[i].setEnabled(true);
                                    }
                                }
                            }
                        }
                        fsm.nextState();
                    }
                    else{
                        for (int i=0; i<25;i++){
                            mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                        }
                        mainFrame.updateTextArea("waiting for "+parts[1]+"'s turn");
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
