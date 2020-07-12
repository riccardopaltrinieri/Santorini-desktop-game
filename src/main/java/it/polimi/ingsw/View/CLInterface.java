package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.utils.InputString;

import javax.swing.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLInterface implements UserInterface {

    private final FSMView fsm;
    private String name;
    private Divinity divinity;
    private int worker;
    private Color color;

    public CLInterface(){
        this.fsm = new FSMView();
    }

    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        Scanner stdin = new Scanner(System.in);
        String outgoingMessage = "Error";

        try {

            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];

            switch (firstWord) {
                case "Device":
                    outgoingMessage = "computer";
                    break;
                case "Welcome!":
                    // Asks the name of the player
                    System.out.println(incomingMessage);
                    do name = stdin.nextLine();
                    while (name == null || name.isBlank());
                    outgoingMessage = name;
                    break;

                case "Decide":
                    // Asks the number of players
                    System.out.println(incomingMessage);
                    outgoingMessage = checkNumber(stdin);
                    break;

                case "Choose":
                    // Asks the name of a divinity
                    System.out.println(incomingMessage);
                    outgoingMessage = checkDivinities(stdin);
                    break;

                case "Your":
                    // Shows to the player his God
                    setDivinity(parts[2]);
                    fsm.setGodName(parts[2]);
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Insert":
                    // Ask the player action according to the fsm
                    // The fsm can't be fooled because there's one also on the server

                    if(parts[1].equals(name)) {

                        // Nothing changed, no need to reprint the board
                        if (fsm.getState() != State.start && fsm.getState() != State.worker && fsm.getState() != State.move) board.printBoardCLI();

                        if (fsm.getState() == State.worker) {
                            // ask to the player which worker he wants to use but don't send anything
                            System.out.println(fsm.getStateStringCLI());
                            checkWorker(stdin, board);
                            fsm.nextState();
                        }

                        if (fsm.getState() == State.start && (divinity == Divinity.Athena || divinity == Divinity.Pan)) {
                            // The Athena's and Pan's power are passive and there is no need to ask
                            outgoingMessage = "usepower";
                        }
                        else {
                            System.out.println(fsm.getStateStringCLI());
                            outgoingMessage = checkAction(stdin);
                        }

                        if(!outgoingMessage.equals("undo")) fsm.nextState();
                        else if (fsm.getState() == State.worker) {
                            // If the player want to reselect the worker it asks again to choose it and ask next action
                            outgoingMessage = update(board);
                        }

                    } else {
                        // If someone else is starting the turn write it
                        if(parts[2].equals("update")) System.out.println(parts[1] +"'s turn: ");
                        else {
                            // If someone else did something reprint the board and write what happened
                            if(!parts[2].equals("wants") && !parts[2].equals("doesn't")){
                                board.printBoardCLI();
                                System.out.println(incomingMessage.substring(7));
                            }
                        }
                        // But don't send anything
                        outgoingMessage = "noMessageToSend";
                    }
                    break;

                case "Wait":
                    System.out.println("Waiting for the other player to choose..");
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Start":
                    if (!parts[1].equals(name))
                        System.out.println("Your " + parts[2] + " opponent is " + parts[1] + "and he will use " + parts[3]);
                    else{
                        color = Color.valueOf(parts[2]);
                        System.out.println("You have " + parts[2] + " color " + "and will use: " + parts[3]);
                    }
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Error:":
                    // Something went wrong with the last action
                    if(parts[1].equals(name)) {
                        System.out.println("Ops, something went wrong");
                        System.out.println(incomingMessage);
                        System.out.println("Please, try again:");

                        // The worker choice is different from other actions
                        if (parts[3].equals("worker")) {
                            fsm.setState(State.worker);
                            System.out.println("Choose again the worker: ");
                            checkWorker(stdin, board);
                            fsm.nextState();
                        }
                        else fsm.prevState();

                        System.out.println(fsm.getStateStringCLI());
                        outgoingMessage = checkAction(stdin);
                        fsm.nextState();
                    } else
                        outgoingMessage = "noMessageToSend";
                    break;

                case "Undo:":

                    if(parts[1].equals(name)) {
                        switch (parts[2]) {
                            case "undid" -> {
                                // The undo action was successful so it asks again to make the action
                                fsm.prevState();
                                board.printBoardCLI();
                                System.out.println(fsm.getStateStringCLI());
                                outgoingMessage = checkAction(stdin);
                                while (outgoingMessage.equals("undo")) {
                                    System.out.println("You can undo only last action, please continue with the play");
                                    System.out.println(fsm.getStateStringCLI());
                                    outgoingMessage = checkAction(stdin);
                                }
                                fsm.nextState();
                            }
                            case "undoing" -> {
                                System.out.println("Processing undo request, please wait..");
                                outgoingMessage = "noMessageToSend";
                            }
                            case "cannot" -> {
                                // The player asked the undo too late, it goes with the scheduled action
                                System.out.println("Your undo request has been rejected");
                                System.out.println(fsm.getStateStringCLI());
                                outgoingMessage = checkAction(stdin);
                                fsm.nextState();
                            }
                        }
                    } else {
                        if(parts[2].equals("undid"))
                            System.out.println(parts[1] + " undid last action");
                        outgoingMessage = "noMessageToSend";
                    }
                    break;

                case "Loser:":
                    if (parts[1].equals(name) && parts[2].equals("loses")) {
                        System.out.println("You lose and cannot play anymore..");
                        System.out.println("Press enter to stop the app");
                        stdin.nextLine();
                        throw new NoSuchElementException("Game ended");
                    }
                    else {
                        board.printBoardCLI();
                        System.out.println(parts[1] + " loses and cannot play anymore..");
                    }

                    outgoingMessage = "noMessageToSend";
                    break;

                case "Winner:":
                    if (parts[1].equals(name) && parts[2].equals("wins")) {
                        board.printBoardCLI();
                        System.out.println("You win and the game it's over..");
                        throw new NoSuchElementException();
                    }
                    else System.out.println(parts[1] + " wins and the game it's over..");

                    System.out.println("Press enter to stop the app");
                    stdin.nextLine();
                    outgoingMessage = "noMessageToSend";
                    break;

                case "This":
                case "You":
                case "Wrong" :
                    // Something has to be rewrite
                    System.out.println(incomingMessage);
                    outgoingMessage = stdin.nextLine();
                    break;

                case "First":
                case "Client":
                    // There was an error and the game end here
                    System.out.println(incomingMessage);
                    throw new NoSuchElementException(incomingMessage);

                case "Timeout:":
                    if (parts[1].equals(name)) {
                       System.out.println("You took to much time to answer, you lose..");
                        throw new NoSuchElementException();
                    }
                    else System.out.println(parts[1] + " took to much time to answer and the game it's over..");

                    outgoingMessage = "noMessageToSend";
                    break;

                default:
                    // Case of other messages that don't need an answer and can be printed
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    break;
            }

            // If none modified the outgoingMessage there was an UNEXPECTED error and the game end
            if(outgoingMessage.equals("Error")) throw new IllegalArgumentException("Unexpected Error in Command Line Interface");

        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return outgoingMessage;
    }

    /**
     * Asks the user to select a worker, then check if the input is right and if the selected worker can move.
     * If it can't asks again to choose.
     */
    private void checkWorker(Scanner stdin, LiteBoard board) {

        while (true) {
            try {
                String inputLine = stdin.nextLine();
                String[] partsInput = inputLine.split(" ");
                InputString action = InputString.valueOf(partsInput[0].toLowerCase());

                if (!(action == InputString.worker)) throw new IllegalArgumentException("you should write 'worker [num]'");
                if (partsInput.length != 2) throw new IllegalArgumentException("you should write 'worker [num]'");
                int worker = Integer.parseInt(partsInput[1]);
                if (!(worker == 1) && !(worker == 2)) throw new IllegalArgumentException("you only have 2 workers");
                if (!board.workerCanMoveCLI(worker, color.ordinal())) throw new IllegalArgumentException(" this worker can't move");
                setWorker(worker);
                return;
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println("Wrong input," + e.getMessage());
                System.out.println("Choose again the worker: ");
            }
        }
    }

    /**
     * Checks if the number of player in input is 2 or 3 according to the rules of the game
     * @return the number in string format
     */
    private String checkNumber(Scanner stdin) {
        String number;
        while(true) {
            number = stdin.nextLine();
            try {
                if ((Integer.parseInt(number) == 2 || Integer.parseInt(number) == 3)) return number;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Please type 2 or 3");
            }
        }
    }

    /**
     * Checks if the action inserted by the player respect all the parameters (like
     * types, rows and columns greater than 0 and smaller than 5 and worker == 1 || 2)
     * and returns it
     */
    private String checkAction(Scanner stdin) {

        String inputLine;

        while(true) {

            try {
                inputLine = stdin.nextLine();
                String[] partsInput = inputLine.split(" ");
                InputString action = InputString.valueOf(partsInput[0].toLowerCase());
                int row, col;

                // If the action is correct the message will be "action row column"

                switch (action) {
                    case move, build -> {
                        if (action == InputString.move && fsm.getState() != State.move) throw new IllegalArgumentException("you can't make a move now");
                        if (action == InputString.build && fsm.getState() != State.build) throw new IllegalArgumentException("you can't build now");
                        if (partsInput.length > 3) throw new IllegalArgumentException("too many parameters");
                        row = Integer.parseInt(partsInput[1]);
                        col = Integer.parseInt(partsInput[2]);
                        if (row < 1 || row > 5 || col < 1 || col > 5) throw new IllegalArgumentException("row and columns should be from 1 to 5");
                        inputLine += " " + getWorker();
                    }
                    case placeworker -> {
                        if(fsm.getState() != State.placeworker) throw new IllegalArgumentException("you can only have 2 workers");
                        row = Integer.parseInt(partsInput[1]);
                        col = Integer.parseInt(partsInput[2]);
                        if (row < 1 || row > 5 || col < 1 || col > 5) throw new IllegalArgumentException("row and columns should be from 1 to 5");
                    }
                    case normal -> {
                        fsm.setPath(Divinity.Default);
                        if (fsm.getState() != State.start) throw new IllegalArgumentException("you're not deciding your turn now");
                        if (partsInput.length > 1) throw new IllegalArgumentException("you wrote too many words");
                    }
                    case usepower -> {
                        fsm.setPath(divinity);
                        if (fsm.getState() != State.start) throw new IllegalArgumentException("you're not deciding your turn now");
                        if (partsInput.length > 1) throw new IllegalArgumentException("you wrote too many words");
                    }
                    case endturn -> {
                        if(fsm.getState() != State.endTurn) throw new IllegalArgumentException("you should not end your turn now");
                    }
                    case undo -> {
                        if(fsm.getState() == State.start || fsm.getState() == State.worker)
                            throw new IllegalArgumentException("You can't undo your action now");
                        if(fsm.getLastState() == State.worker) fsm.setState(State.worker);
                    }
                    default -> throw new IllegalArgumentException();
                }

                // If the input is correct it can be send
                return inputLine.toLowerCase();

            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input, " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong input, too few parameters");
            }
        }
    }

    /**
     * Checks if the name of a God inserted by the player is correct and present in the list
     * of divinities chosen and returns it
     */
    private String checkDivinities(Scanner stdin) {
        String inputLine;

        inputLine = stdin.nextLine();

            while (true) {
                try {
                    Divinity.valueOf(inputLine);
                    return inputLine;
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong input: reinsert your Divinity:");
                    inputLine = stdin.nextLine();
                }
            }
    }

    // *************************** GETTER AND SETTER ********************************

    public void setWorker(int worker) {
        this.worker = worker;
    }
    public int getWorker() {
        return worker;
    }
    public void setDivinity(String divinity) {
        this.divinity = Divinity.valueOf(divinity);
    }
}
