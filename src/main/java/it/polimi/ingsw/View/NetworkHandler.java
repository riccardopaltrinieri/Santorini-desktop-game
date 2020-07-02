package it.polimi.ingsw.View;


import java.io.*;
import java.net.Socket;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains a socket which connect the client with the server. Its main role is
 * to pass messages from server to the User Interface and vice versa writing on
 * the log file all the messages passing through it.
 *
 * @see UserInterface
 * @see Socket
 */
public class NetworkHandler {

    private final String ip;
    private final int port;
    private UserInterface userInterface;

    /**
     * Constructor that saves the ip and port of the server to create the socket
     */
    public NetworkHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    /**
     * Receives from the socket a board filled with all the needed data from the game.
     * The board is passed to the UI (which could be either on command line or graphic)
     * that return the message to send back to the server. <br>
     * It also write on the log file all the incoming/outgoing messages of the client.
     * @throws IOException if an I/O error occurs when creating or reading from the socket
     */
    public void run() throws IOException {

        Socket socket = new Socket(ip, port);
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        FileOutputStream logFile = new FileOutputStream(new File("log.txt"), true);
        System.out.println("Connection established");

        Scanner stdin = new Scanner(System.in);
        String outgoingMessage;
        LiteBoard board;
        try{
            //noinspection InfiniteLoopStatement
            while (true) {

                board = (LiteBoard) socketIn.readObject();
                outgoingMessage = userInterface.update(board);


                // The clients write in the same file so i lock it
                try (FileLock ignored = logFile.getChannel().lock()) {

                    // Write the bytes.
                    logFile.write((board.getMessage() + "\n").getBytes());
                    if (!outgoingMessage.equals("noMessageToSend"))
                        logFile.write(("-> " + outgoingMessage + "\n").getBytes());
                } catch (OverlappingFileLockException e) {
                    try {
                        // If the log file is already locke wait a bit
                        Thread.sleep(100);
                        // and retry
                    } catch (InterruptedException ex) {
                        throw new InterruptedIOException("Interrupted while waiting for a file lock.");
                    }
                }

                if (!outgoingMessage.equals("noMessageToSend")) {
                    socketOut.reset();
                    socketOut.writeUTF(outgoingMessage);
                    socketOut.flush();
                }
            }

        } catch(NoSuchElementException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            System.out.println("Connection closed from the server side");

            stdin.close();
            socketOut.close();
            socketIn.close();
            socket.close();
        }
    }

    public void setUserInterface(UserInterface ui) {
        this.userInterface = ui;
    }
}