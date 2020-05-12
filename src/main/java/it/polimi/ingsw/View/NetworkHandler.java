package it.polimi.ingsw.View;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class NetworkHandler {

    private String ip;
    private int port;
    private UserInterface userInterface;

    public NetworkHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void setUserInterface(UserInterface ui) {
        this.userInterface = ui;
    }


    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String incomingMessage;
        String outgoingMessage;
        //TODO togliere inizializzazione perchè board sarà == socketIn.getBoard()
        LiteBoard board = new LiteBoard("", null);

        try{
            //noinspection InfiniteLoopStatement
            while (true){
                //TODO non so come si fa la prossima riga
                //board = socketIn.nextLiteBoard();
                //TODO anche la prossima sarà implicita nella precedente quindi da togliere
                board.setMessage(socketIn.nextLine());
                outgoingMessage = userInterface.update(board);
                if(!outgoingMessage.equals("noMessageToSend")) {
                    socketOut.println(outgoingMessage);
                    socketOut.flush();
                }
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

}