package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.InputString;
import it.polimi.ingsw.utils.NamesDivinities;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLInterface implements UserInterface {

    private final FSMView fsm = new FSMView();
    private String name;
    private int worker;

    public CLInterface(){
    }

    /**
     * Method that get as input the board to show to the player and asks to him to write
     * the action he must execute in that moment, according to an internal Finite State Machine
     * which follow the identical one on the server.
     * The action inserted by the player is returned to be sent to the server
     * @param board that will be showed to the player based on the message written in it
     * @return the action of the player already checked
     */
    @Override
    public String update(LiteBoard board) {
        String incomingMessage = board.getMessage();
        Scanner stdin = new Scanner(System.in);
        String outgoingMessage = "Error";

        try {

            String[] parts = incomingMessage.split(" ");
            String firstWord = parts[0];

            // board.printBoardCLI();
            // Assign the divinity to the fsm
            switch (firstWord) {
                case "Welcome!":
                    // Asks the name of the player
                    System.out.println(incomingMessage);
                    name = stdin.nextLine();
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
                    fsm.setPath(parts[2]);
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Insert":
                    // Ask the player action according to the fsm
                    // The fsm can't be fooled because there's one also on the server
                    if (fsm.getState() == State.worker) {
                        // ask to the player which worker he wants to use but don't send anything
                        System.out.println(getStringFSM());
                        checkAction(stdin);
                    }
                    String toPrint = getStringFSM();
                    if (!toPrint.equals("Place your worker on the map: (write 'placeworker [row] [column]')")) board.printBoardCLI();
                    System.out.println(toPrint);
                    outgoingMessage = checkAction(stdin);
                    if (fsm.getState() == State.endTurn) {
                        System.out.println("Turn ended");
                        fsm.setState(State.start);
                    }
                    break;

                case "Wait":
                    System.out.println("Waiting for the other player to choose..");
                    outgoingMessage = "noMessageToSend";
                    break;

                case "player":
                    // Use the name og the player to know who has to play
                    if (parts[1].equals(name) && parts[2].equals("moves")) System.out.println("Turn started");
                    else if (parts[2].equals("moves")) System.out.println(parts[1] + "'s turn..");
                    else {
                        System.out.println("You lose and cannot play anymore..");
                        throw new NoSuchElementException();
                    }
                    outgoingMessage = "noMessageToSend";
                    break;

                case "Error":
                    // Something went wrong with the last action
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    fsm.prevState();
                    break;

                default:
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    break;
            }

            if(outgoingMessage.equals("Error")) throw new IllegalArgumentException();

        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return outgoingMessage;
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
                String outputLine = inputLine;
                String[] partsInput = inputLine.split(" ");
                InputString action = InputString.valueOf(partsInput[0].toLowerCase());

                // If the action is correct the message will be "action row column"

                switch (action) {

                    case worker:
                        int worker = Integer.parseInt(partsInput[1]);
                        if (!(worker == 1) && !(worker == 2)) throw new IllegalArgumentException();
                        setWorker(worker);

                        outputLine = inputLine;
                        break;
                    case move:
                    case build:
                    case supermove:
                    case superbuild:
                        // Add the worker chose before and continue the check
                        inputLine += " " + getWorker();
                    case placeworker:
                        int row = Integer.parseInt(partsInput[1]);
                        int col = Integer.parseInt(partsInput[2]);
                        if (row < 1 || row > 5 || col < 1 || col > 5) throw new IllegalArgumentException();
                        outputLine = inputLine;
                        break;

                    case normal:
                    case usepower:
                        if(partsInput.length > 1) throw new IllegalArgumentException();
                        outputLine = inputLine;
                        break;
                }

                // If the input is correct it can be send
                return outputLine.toLowerCase();

            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println("Wrong input: reinsert your move");
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
                    NamesDivinities.valueOf(inputLine);
                    return inputLine;
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong input: reinsert your Divinity:");
                    inputLine = stdin.nextLine();
                }
            }
    }

    /**
     * Return the string to show to the player according to the fsm state
     */
    public String getStringFSM() {
        String line;
        switch (fsm.getState()) {
            case placeworker:
                line = "Place your worker on the map: (write 'placeworker [row] [column]')";
                break;
            case start:
                line = "Do you want to use the God power or going with the normal turn? (usepower/normal)";
                break;
            case worker:
                line = "Choose the worker that you want to move and build with: (write 'worker 1' or 'worker 2]')";
                break;
            case supermove:
            case move:
                line = "Where do you want to move? (write 'move [row] [column]')";
                break;
            case superbuild:
            case build:
                line = "Where do you want to build? (write 'build [row] [column]')";
                break;
            default:
                line = "Error";
                break;
        }
        fsm.nextState();

        return line;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public int getWorker() {
        return worker;
    }
}
