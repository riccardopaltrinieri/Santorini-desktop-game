package it.polimi.ingsw.View;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());

        Scanner stdin = new Scanner(System.in);
        String incomingMessage;
        String outgoingMessage;
        LiteBoard board;
        //TODO togliere inizializzazione perchè board sarà == socketIn.getBoard()
        //LiteBoard board = new LiteBoard("", null);

        try{
            //noinspection InfiniteLoopStatement
            while (true){

                board = (LiteBoard) socketIn.readObject();
                outgoingMessage = userInterface.update(board);
                if (!outgoingMessage.equals("noMessageToSend"))
                {
                    socketOut.reset();
                    socketOut.writeUTF(outgoingMessage);
                    socketOut.flush();
                }

                //TODO non so come si fa la prossima riga. HO COMMENTATO PER COMMITARE, SERIALIZZAZIONE ( WORK IN PROGRESS)
                //board = socketIn.nextLiteBoard();
                //TODO anche la prossima sarà implicita nella precedente quindi da togliere
              //  board.setMessage(socketIn.nextLine());
              //  outgoingMessage = userInterface.update(board);
              //  if(!outgoingMessage.equals("noMessageToSend")) {
                //    socketOut.println(outgoingMessage);
               //     socketOut.flush();
             //   }
            }
        } catch(NoSuchElementException | ClassNotFoundException e){
            System.out.println("Connection closed from the server side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}