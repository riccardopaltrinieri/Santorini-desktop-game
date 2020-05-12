package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.InputString;

import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLInterface implements UserInterface {

    private final FSMView fsm = new FSMView();
    private String name;

    public CLInterface(){
    }

    @Override
    public String update(String incomingMessage) {

        Scanner stdin = new Scanner(System.in);
        String outgoingMessage = "Error";

        try {
                String[] parts = incomingMessage.split(" ");
                String firstWord = parts[0];

            switch (firstWord) {
                case "Welcome!":
                    // Ask the name of the player
                    System.out.println(incomingMessage);
                    name = stdin.nextLine();
                    outgoingMessage = name;
                    break;
                case "Decide":
                    // Ask the number of players
                    System.out.println(incomingMessage);
                    outgoingMessage = checkNumber(stdin);
                    break;
                case "Choose":
                    // Ask the name of a divinity or the number of players
                    System.out.println(incomingMessage);
                    outgoingMessage = checkDivinities(stdin);
                    break;
                case "Your":
                    // Assign the divinity to the fsm
                    fsm.setPath(parts[2]);
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
                    break;
                case "Insert":
                    // Ask the player action according to the fsm
                    // The fsm can't be fooled because there's one also on the server

                    System.out.println(getStringFSM());
                    outgoingMessage = checkAction(stdin);

                    if(fsm.getState() == State.endTurn) {
                        System.out.println("Turn ended");
                        fsm.setState(State.start);
                    }
                    break;
                case "Wait":
                    System.out.println("Waiting for the other player to choose..");
                    outgoingMessage = "noMessageToSend";
                    break;
                case "player":
                    if (parts[1].equals(name) && parts[2].equals("moves")) System.out.println("Turn started");
                    else if(parts[2].equals("moves")) System.out.println(parts[1] + "'s turn..");
                    else {
                        System.out.println("You lose and cannot play anymore..");
                        throw new NoSuchElementException();
                    }
                    outgoingMessage = "noMessageToSend";
                    break;
                default:
                    System.out.println(incomingMessage);
                    outgoingMessage = "noMessageToSend";
            }

            if(outgoingMessage.equals("Error")) throw new IllegalArgumentException();

        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return outgoingMessage;
    }

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

    private String checkAction(Scanner stdin) {

        String inputLine;

        // private int moves=0;   // A variable that can be used to count the attempts

        while(true) {

            try {
                inputLine = stdin.nextLine();
                String[] partsInput = inputLine.split(" ");
                InputString action = InputString.valueOf(partsInput[0].toLowerCase());

                // If the action is correct the message will be "action row column (worker)"
                switch (action) {
                    case move:
                    case build:
                    case supermove:
                    case superbuild:
                        if(partsInput.length < 4) throw new IllegalArgumentException();
                        int worker = Integer.parseInt(partsInput[3]);
                        if (!(worker == 0) && !(worker == 1)) throw new IllegalArgumentException();
                        // no break in order to continue the check !!
                    case placeworker:
                        if(partsInput.length < 3) throw new IllegalArgumentException();
                        int row = Integer.parseInt(partsInput[1]);
                        int col = Integer.parseInt(partsInput[2]);
                        if (row < 0 || row > 4 || col < 0 || col > 4) throw new IllegalArgumentException();
                        break;
                    case normal:
                    case usepower:
                        if(partsInput.length > 1) throw new IllegalArgumentException();
                        break;
                }

                // If the input is correct it can be send
                return inputLine.toLowerCase();

            } catch (IllegalArgumentException e) {
                System.out.println("Wrong input: reinsert your move");
                //moves++;
            }
        }
    }

    private String checkDivinities(Scanner stdin) {
        //TODO Divinity name and player number check
        return stdin.nextLine();
    }

    public String getStringFSM() {
        String line;
        switch (fsm.getState()) {
            case placeworker:
                line = "Place your worker on the map: (write 'placeworker [row] [column]')";
                break;
            case start:
                line = "Do you want to use the God power or going with the normal turn? (usepower/normal)";
                break;
            case move:
                line = "Where do you want to move? (write 'move [row] [column] [worker]')";
                break;
            case build:
                line = "Where do you want to build? (write 'build [row] [column] [worker]')";
                break;
            default:
                line = "Error";
                break;
        }
        fsm.nextState();

        return line;
    }
}
