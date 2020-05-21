package it.polimi.ingsw.View;


import java.io.*;
import java.net.Socket;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
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
        FileOutputStream logFile = new FileOutputStream(new File("log.txt"), true);

        Scanner stdin = new Scanner(System.in);
        String outgoingMessage;
        LiteBoard board;
        try{
            //noinspection InfiniteLoopStatement
            while (true) {

                board = (LiteBoard) socketIn.readObject();
                outgoingMessage = userInterface.update(board);


                // The clients write in the same file so i lock it
                try (FileLock lock = logFile.getChannel().lock()) {

                    // Write the bytes.
                    logFile.write((board.getMessage() + "\n").getBytes());
                    if (!outgoingMessage.equals("noMessageToSend"))
                        logFile.write(("-> " + outgoingMessage + "\n").getBytes());
                } catch (OverlappingFileLockException ofle) {
                    try {
                        // Wait a bit
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        throw new InterruptedIOException("Interrupted waiting for a file lock.");
                    }
                }

                if (!outgoingMessage.equals("noMessageToSend")) {
                    socketOut.reset();
                    socketOut.writeUTF(outgoingMessage);
                    socketOut.flush();
                }
            }

        } catch(NoSuchElementException | ClassNotFoundException e) {
            System.out.println("Connection closed from the server side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}