package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.State;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLInterface {

    private String ip;
    private int port;
    private FSMView fsm = new FSMView();

    public CLInterface(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            //noinspection InfiniteLoopStatement
            while (true){
                String[] parts = socketLine.split(" ");
                String firstWord = parts[0];

                if (firstWord.equals("Welcome!") || firstWord.equals("Choose") || firstWord.equals("Insert")) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
                socketLine = socketIn.nextLine();
                if(firstWord.equals("Insert")) System.out.println(getStringFSM());
                else System.out.println(socketLine);
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the server side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public String getStringFSM() {
        String line;
        switch (fsm.getState()) {
            case start:
                line = "Do you want to use the God power?";
                break;
            case move:
                line = "Where do you want to move?";
                break;
            case build:
                line = "Where do you want to build?";
                break;
            case endTurn:
                line = "Turn ended.";
                break;
            default:
                line = "Error";
                break;
        }
        if(fsm.getState() == State.endTurn) fsm.setState(State.start);
        else fsm.nextState();
        return line;
    }
}
