package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.View.Graphics.BoardButtonListener;
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
    private Color color;
    private String firstGodToRemove;
    private String secondGodToRemove;
    private int numPlayer;
    private int selectedWorkerIndex;

    private BoardButtonListener boardListener = new BoardButtonListener(fsm,color,mainFrame);

    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        String outgoingMessage = "noMessageToSend";

        try{
            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];
            mainFrame.updateTextArea(incomingMessage);

            board.printBoardGUI(mainFrame);

            switch (firstWord){

                case "Start" :
                //take the other's player info
                    if (!parts[1].equals(name)) {
                        mainFrame.updatePlayerInfoTextArea("Your " + parts[2] + " opponent is " + parts[1] + "\nHe will use " + parts[3]);
                    }
                    else if (parts[1].equals(name)){
                        color = Color.stroingToColor(parts[2]);
                    }
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Welcome!":
                    //Ask gor the player's name
                    //TODO gestisci bottone cancel o toglilo
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public synchronized void  run() {
                            mainFrame.init();
                            for (int i=0;i<25;i++){
                                mainFrame.getActiveBoardButtons()[i].addActionListener(boardListener);
                            }
                            mainFrame.getYesButton().addActionListener(boardListener);
                            mainFrame.getNoButton().addActionListener(boardListener);
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
                        while(!(outgoingMessage.equals("2")||(outgoingMessage.equals("3")))){
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


                case "Insert":
                    //ask to the player for the next move according to the FSM

                    if (fsm.getState() == State.worker) {
                        // ask to the player which worker he wants to use but don't send anything
                        mainFrame.updateTextArea("Click on the worker that you want to use");
                        for (int i=0; i<25;i++){
                            if((mainFrame.getActiveBoardButtons()[i].getHaveWorker())&&(mainFrame.getActiveBoardButtons()[i].getWorkerColor()==color)){
                                mainFrame.getActiveBoardButtons()[i].setEnabled(true);
                            }
                            else {
                                mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                            }
                        }
                        selectedWorkerIndex = (mainFrame.getChosenButtonCoordinate()[0]-1)*5+mainFrame.getChosenButtonCoordinate()[1]-1;
                        fsm.nextState();
                    }

                    if (parts[1].equals(name)){
                        //placeWorker
                        if (fsm.getState()==State.placeworker){
                            for (int i=0; i<25;i++){
                                mainFrame.getActiveBoardButtons()[i].setEnabled(!mainFrame.getActiveBoardButtons()[i].getHaveWorker());
                            }
                            int[] coordinate = mainFrame.getChosenButtonCoordinate();
                            outgoingMessage ="placeworker "+coordinate[0]+" "+coordinate[1];
                        }

                        //Start
                        if (fsm.getState()==State.start){
                            mainFrame.updateTextArea("Do you want to use your Godpower?");
                            mainFrame.addYesOrNo();
                            for (int i=0;i<25;i++){
                                mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                            }

                            outgoingMessage=mainFrame.getYesOrNoResponse();
                            mainFrame.removeYesOrNo();
                        }

                        //Move
                        if (fsm.getState()==State.move){
                            mainFrame.updateTextArea("Select where you want to move your worker");
                            for (int i=0; i<25; i++){
                                if (mainFrame.getActiveBoardButtons()[selectedWorkerIndex].canMoveTo(mainFrame.getActiveBoardButtons()[i])){
                                    mainFrame.getActiveBoardButtons()[i].setSelectableCell(true);
                                    mainFrame.getActiveBoardButtons()[i].setEnabled(true);
                                    mainFrame.getActiveBoardButtons()[i].repaint();
                                }
                                else{
                                    mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                                }
                            }
                            int[] coordinate = mainFrame.getChosenButtonCoordinate();
                            selectedWorkerIndex = (coordinate[0]-1)*5+coordinate[1]-1;
                            outgoingMessage ="move "+coordinate[0]+" "+coordinate[1]+" "+mainFrame.getActiveBoardButtons()[selectedWorkerIndex].getWorkerNum()%2+1;
                        }

                        //Build
                        if(fsm.getState()==State.build){
                            mainFrame.updateTextArea("select where you want to buld");
                            for (int i=0;i<25;i++){
                                if(mainFrame.getActiveBoardButtons()[selectedWorkerIndex].canBuildIn(mainFrame.getActiveBoardButtons()[i])){
                                    mainFrame.getActiveBoardButtons()[i].setSelectableCell(true);
                                    mainFrame.getActiveBoardButtons()[i].setEnabled(true);
                                    mainFrame.getActiveBoardButtons()[i].repaint();
                                }
                                else{
                                    mainFrame.getActiveBoardButtons()[i].setEnabled(false);
                                }
                            }
                            int [] coordinate = mainFrame.getChosenButtonCoordinate();
                            outgoingMessage = "move "+coordinate[0]+" "+coordinate[1]+" "+mainFrame.getActiveBoardButtons()[selectedWorkerIndex].getWorkerNum()%2+1;
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

    public State getFsmState() {
        return fsm.getState();
    }
}

//TODO gestire meglio messaggi di infotext per comunicare meglio
