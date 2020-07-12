package it.polimi.ingsw.View;

import it.polimi.ingsw.View.Graphics.*;
import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import java.util.NoSuchElementException;

/**
 * this class include all the logic of GUI and handle what have to be displayed in the frame
 * based on the state of the game and handle the communication forwarding the message through the UserInterface
 * and receiving the incoming one.
 */
public class GUIHandler implements UserInterface {

    final MainFrame mainFrame= new MainFrame();
    ChoosePanel choosePanel = new ChoosePanel();
    ChooseBetweenPanel chooseBetweenPanel = new ChooseBetweenPanel();
    private final FSMView fsm = new FSMView();

    private Color color;
    private Divinity divinity;
    private String name;
    private String lastMessage;
    private String firstGodToRemove=null;
    private String secondGodToRemove=null;
    private int selectedWorkerIndex;
    private int previousCellMoveIndex=-1;
    private int previousCellBuildIndex=-1;
    private int numPlayer;


    private final BoardButtonListener boardListener = new BoardButtonListener(fsm,color,mainFrame);
    private final UndoEndlistener undoEndlistener = new UndoEndlistener(mainFrame);

    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        String outgoingMessage = "Error";

        try{
            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];


            switch (firstWord){

                case "Error:":
                    board.printBoardGUI(mainFrame);
                    //Error
                    if (parts[1].equals(name)) {
                        JOptionPane.showMessageDialog(mainFrame, "Ops, something went wrong\n" +incomingMessage+ "\nPlease try again");

                        if (parts[3].equals("worker")) {
                            fsm.setState(State.worker);
                            checkAction(board);
                            fsm.nextState();
                        }
                        else fsm.prevState();

                        outgoingMessage = checkAction(board);
                        while (outgoingMessage.equals("undo")) {
                            JOptionPane.showMessageDialog(mainFrame, "You can't undo a mistake");
                            mainFrame.updateTextArea(fsm.getStateStringGUI());
                            outgoingMessage = checkAction(board);
                        }
                        fsm.nextState();

                        incomingMessage = lastMessage;
                    } else outgoingMessage = "noMessageToSend";
                    break;

                case "Start" :
                //take the other's player info

                    if (!parts[1].equals(name)) {
                        mainFrame.updatePlayerInfoTextArea("Your " + parts[2] + " opponent is " + parts[1] + "\nHe will use " + parts[3]);
                    }
                    else {
                        color = Color.valueOf(parts[2]);
                    }
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Welcome!":
                    //Ask gor the player's name
                    //TODO gestisci bottone cancel o toglilo
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public synchronized void  run() {
                            mainFrame.startingInit();

                        }
                    });

                    mainFrame.updateTextArea("Insert your name in the dialog");
                    do {
                        outgoingMessage = (String)JOptionPane.showInputDialog(
                                mainFrame,
                                "Insert your name here",
                                "Player's name",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");
                    } while( outgoingMessage==null || outgoingMessage.equals("") );

                    name = outgoingMessage;
                break;

                case "This":

                    mainFrame.updateTextArea("Insert your name in the dialog");
                    do {
                        outgoingMessage = (String)JOptionPane.showInputDialog(
                                mainFrame,
                                "This name is already taken, please choose another one",
                                "Player's name",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");
                    } while( outgoingMessage==null || outgoingMessage.equals("") );

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
                    do {
                        if (result == JOptionPane.YES_OPTION) {
                            outgoingMessage = "2";
                            numPlayer = 2;
                        } else if (result == JOptionPane.NO_OPTION) {
                            outgoingMessage = "3";
                            numPlayer = 3;
                        }
                    } while(!(outgoingMessage.equals("2")||(outgoingMessage.equals("3"))));

                    break;

                case "Choose":
                    mainFrame.removeStartingPanel();
                    mainFrame.remove(choosePanel);
                    choosePanel = new ChoosePanel();
                    //ask the player to choose the divinity
                    if (parts[1].equals("the")) {
                        if (firstGodToRemove != null) choosePanel.removeDivinityString(firstGodToRemove);
                        if (secondGodToRemove != null) choosePanel.removeDivinityString(secondGodToRemove);
                        // Ask the name of a divinity or the number of players
                        choosePanel.init();
                        mainFrame.add(choosePanel);
                        mainFrame.pack();

                        outgoingMessage = choosePanel.getChosenDivinity();
                        if (parts[2].equals("first")) {
                            firstGodToRemove = outgoingMessage;
                        } else if (parts[2].equals("second")) {
                            secondGodToRemove = outgoingMessage;
                        }
                        if (((numPlayer==2)&&(parts[2].equals("second"))||(numPlayer==3)&&(parts[2].equals("third")))){
                            startGameFrame();
                        }
                    }
                    else if (parts[1].equals("your")){
                        if (parts.length==6){
                            chooseBetweenPanel.init(parts[4],parts[5]);
                        }
                        else{
                            chooseBetweenPanel.init(parts[4],parts[5],parts[6]);
                        }
                        mainFrame.removeStartingPanel();
                        mainFrame.add(chooseBetweenPanel);
                        mainFrame.pack();

                        outgoingMessage= chooseBetweenPanel.getChosenDivinity();
                        startGameFrame();
                    }

                    break;

                case "You":
                    String[] players;
                    if(parts.length == 8 ) {
                        // There are only 2 players
                        players = new String[]{parts[6], parts[7]};
                        do {
                            int chosenPlayer = JOptionPane.showOptionDialog(
                                    mainFrame,
                                    "Decide the first Player",
                                    "First Player",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //no custom icon
                                    players,  //button titles
                                    players[0] //default button
                            );
                            if (chosenPlayer == JOptionPane.YES_OPTION) {
                                outgoingMessage = parts[6];
                            } else if (chosenPlayer == JOptionPane.NO_OPTION) {
                                outgoingMessage = parts[7];
                            }
                        } while (outgoingMessage.isEmpty());
                    }
                    else {
                        // There are 3 players
                        players = new String[]{parts[6], parts[7], parts[8]};
                        do {
                            int chosenPlayer = JOptionPane.showOptionDialog(
                                    mainFrame,
                                    "Decide the first Player",
                                    "First Player",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //no custom icon
                                    players,  //button titles
                                    players[0] //default button
                            );
                            if (chosenPlayer == JOptionPane.YES_OPTION) outgoingMessage = parts[6];
                            else if (chosenPlayer == JOptionPane.NO_OPTION) outgoingMessage = parts[7];
                            else outgoingMessage = parts[8];

                        } while (outgoingMessage.isEmpty());
                    }
                    break;

                case "Your":
                    // Shows to the player his God
                    mainFrame.updateGodCard(parts[2]);
                    divinity = Divinity.valueOf(parts[2]);
                    fsm.setGodName(parts[2]);
                    outgoingMessage = "noMessageToSend";
                    break;


                case "Insert":

                    // Updates the board
                    board.printBoardGUI(mainFrame);

                    if (parts[1].equals(name)){
                        //ask to the player for the next move according to the FSM
                        mainFrame.updateTextArea(fsm.getStateStringGUI());

                        if (fsm.getState() == State.worker) {
                            checkAction(board);
                            fsm.nextState();
                        }

                        outgoingMessage = checkAction(board);

                        if (!outgoingMessage.equals("undo")) fsm.nextState();
                    } else {
                        for (BoardButton button : mainFrame.getActiveBoardButtons()){
                            button.setEnabled(false);
                        }
                        if(parts[2].equals("update")) mainFrame.updateTextArea(parts[1] +"'s turn: ");
                        outgoingMessage = "noMessageToSend";
                    }
                    break;

                case "Undo:":
                    board.printBoardGUI(mainFrame);

                    if(parts[1].equals(name)) {
                       switch(parts[2]) {
                           case "undid" -> {
                               fsm.prevState();
                               mainFrame.updateTextArea(fsm.getStateStringGUI());
                               outgoingMessage = checkAction(board);
                               while (outgoingMessage.equals("undo")) {
                                   JOptionPane.showMessageDialog(mainFrame, "You can undo only last action, please continue with the play");
                                   mainFrame.updateTextArea(fsm.getStateStringGUI());
                                   outgoingMessage = checkAction(board);
                               }
                               fsm.nextState();
                           }
                           case "undoing" -> {
                               mainFrame.updateTextArea("Processing undo request, please wait..");
                               outgoingMessage = "noMessageToSend";
                           }
                           case "cannot" -> {
                               JOptionPane.showMessageDialog(mainFrame, "Your undo request has been rejected");
                               mainFrame.updateTextArea(fsm.getStateStringGUI());
                               outgoingMessage = checkAction(board);
                               fsm.nextState();
                           }
                       }
                    }
                    else {
                        if(parts[2].equals("undid"))
                            mainFrame.updateTextArea(parts[1] + " undid last action");
                        outgoingMessage = "noMessageToSend";
                    }
                    break;

                case "Loser:":
                        if (parts[1].equals(name) && parts[2].equals("loses")) {
                            JOptionPane.showMessageDialog(mainFrame, "You lose and cannot play anymore..");

                            throw new NoSuchElementException("Game ended");
                        }
                        else {
                            JOptionPane.showMessageDialog(mainFrame, parts[1] + " loses and cannot play anymore..");
                        }

                        outgoingMessage = "noMessageToSend";
                        break;

                case "Winner:":
                    board.printBoardGUI(mainFrame);
                    if (parts[1].equals(name) && parts[2].equals("wins")) {
                        JOptionPane.showMessageDialog(mainFrame, "You win and the game it's over..");
                        throw new NoSuchElementException();
                    }
                    else JOptionPane.showMessageDialog(mainFrame, parts[1] + " wins and the game it's over..");

                    outgoingMessage = "noMessageToSend";
                    break;

                case "First":
                case "Client":
                    JOptionPane.showMessageDialog(mainFrame, incomingMessage);
                    throw new NoSuchElementException(incomingMessage);

                case "Timeout:":
                    if (parts[1].equals(name)) {
                        JOptionPane.showMessageDialog(mainFrame, "You took to much time to answer, you lose..");
                         throw new NoSuchElementException();
                    }
                    else JOptionPane.showMessageDialog(mainFrame, parts[1] + " took to much time to answer and the game it's over..");

                     outgoingMessage = "noMessageToSend";
                     break;

                default:
                    outgoingMessage = "noMessageToSend";
                    break;
            }
            lastMessage = incomingMessage;
            if (outgoingMessage.equals("Error")) throw new IllegalArgumentException();

            return outgoingMessage;
        }
        catch (IllegalArgumentException | InterruptedException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * initialize the main panel removing everything that was on the screen before
     */
    private void startGameFrame(){
        mainFrame.remove(choosePanel);
        mainFrame.remove(chooseBetweenPanel);
        mainFrame.init();
        for (BoardButton button : mainFrame.getActiveBoardButtons()){
            button.addActionListener(boardListener);
        }
        mainFrame.getEndTurnButton().addActionListener(undoEndlistener);
        mainFrame.getPowerButton().addActionListener(boardListener);
        mainFrame.getDefaultButton().addActionListener(boardListener);
        mainFrame.getUndoButton().addActionListener(undoEndlistener);
        mainFrame.updateTextArea("waiting for other player");
    }

    /**
     * Checks if the action inserted by the player respect all the parameters (like
     * types, rows and columns greater than 0 and smaller than 5 and worker == 1 || 2)
     * and returns it
     */
    private String checkAction(LiteBoard board) throws IllegalArgumentException, InterruptedException {

        switch(fsm.getState()) {

            case placeworker -> {
                mainFrame.getUndoButton().setEnabled(true);
                for (BoardButton button : mainFrame.getActiveBoardButtons()) {
                    button.setEnabled(!button.getHaveWorker());
                }
                int[] coordinate = mainFrame.getChosenButtonCoordinate();

                if(mainFrame.getUndo() && coordinate[0] == -1) {
                    mainFrame.setUndo(false);
                    return "undo";
                }

                return "placeworker " + coordinate[0] + " " + coordinate[1];
            }

            case start -> {
                mainFrame.getUndoButton().setEnabled(false);
                //Asks if you want to use the godPower
                if ((divinity == Divinity.Athena) || (divinity == Divinity.Pan)) {
                    return "usepower";
                } else {
                    for (BoardButton button : mainFrame.getActiveBoardButtons()) {
                        button.setEnabled(false);
                    }
                    mainFrame.updateTextArea(fsm.getStateStringGUI());
                    mainFrame.addYesOrNo();
                    String message = mainFrame.getYesOrNoResponse();
                    if (message.equals("usepower")) fsm.setPath(divinity);
                    else fsm.setPath(Divinity.Default);
                    mainFrame.removeYesOrNo();
                    return message;
                }
            }

            case worker -> {
                mainFrame.getUndoButton().setEnabled(false);
                // ask to the player which worker he wants to use but don't send anything
                for (BoardButton button : mainFrame.getActiveBoardButtons()) {
                    if ((button.getHaveWorker()) && (button.getWorkerColor() == color)) {
                        button.setSelectableCell(board.workerCanMove(button.getRow()-1, button.getColumn()-1));
                    } else
                        button.setSelectableCell(false);
                }

                int[] coordinate = mainFrame.getChosenButtonCoordinate();
                selectedWorkerIndex = mainFrame.getActiveButton((coordinate[0] - 1) * 5 + coordinate[1] - 1).getWorkerNum();
                return "";
            }

            case move, build -> {
                mainFrame.getUndoButton().setEnabled(true);
                mainFrame.updateTextArea(fsm.getStateStringGUI());
                mainFrame.updateActiveBoardButtons(fsm.getState(), fsm.getPath(), selectedWorkerIndex);

                int workerIndex = selectedWorkerIndex % 2 + 1;
                int[] coordinate = mainFrame.getChosenButtonCoordinate();

                if(mainFrame.getUndo() && coordinate[0] == -1) {
                    if(fsm.getLastState() == State.worker) {
                        fsm.setState(State.worker);
                        mainFrame.setUndo(false);
                        checkAction(board);
                        fsm.nextState();
                        return checkAction(board);
                    }
                    mainFrame.setUndo(false);
                    return "undo";
                }

                // If the divinity is Artemis the worker can't move in the initial position
                if(fsm.getState() == State.move && fsm.getPath() == Divinity.Artemis){
                    if(previousCellMoveIndex == -1) {
                        previousCellMoveIndex = (coordinate[0]-1) * 5 + coordinate[1]-1;
                        mainFrame.getActiveButton(previousCellMoveIndex).setSelectableCell(false);
                    }
                    else previousCellMoveIndex = -1;
                }
                // If the divinity is Demeter the worker can't build in the same position
                else if(fsm.getState() == State.build && fsm.getPath() == Divinity.Demeter) {
                    if (previousCellBuildIndex == -1) {
                        previousCellBuildIndex = (coordinate[0] - 1) * 5 + coordinate[1] - 1;
                        mainFrame.getActiveButton(previousCellBuildIndex).setSelectableCell(false);
                    } else previousCellBuildIndex = -1;
                }

                return fsm.getState() + " " + coordinate[0] + " " + coordinate[1] + " " + workerIndex;
            }
            case endTurn -> {
                mainFrame.getUndoButton().setEnabled(true);
                mainFrame.getEndTurnButton().setEnabled(true);
                synchronized (mainFrame) {
                    mainFrame.wait();
                    mainFrame.getUndoButton().setEnabled(false);
                    mainFrame.getEndTurnButton().setEnabled(false);
                    if(mainFrame.getUndo()){
                        mainFrame.setUndo(false);
                        return "undo";
                    } else
                        return "endturn";
                }
            }

            default -> {
                return "Error";
            }
        }
    }

}

//TODO gestire meglio messaggi di infotext per comunicare meglio
