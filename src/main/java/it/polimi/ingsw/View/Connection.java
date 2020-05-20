package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable implements Runnable, Observer {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final Server server;
    private String name;
    private boolean active;
    private boolean playerTurn;
    private LiteBoard liteBoard;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
    }

    public ObjectInputStream getIn(){
        return this.in;
    }
    private synchronized boolean isActive(){
        return active;
    }


    public void send(LiteBoard board){
        try {
            out.reset();
            out.writeObject(board);
            out.flush();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(){
        String read = "";
        while (read.isEmpty()) {
            try {
                read = (in.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return read;
    }

    public synchronized void closeConnection(){
        send( new LiteBoard("Connection closed from the server side"));
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            send( new LiteBoard("Welcome! What's your name?"));
            name = readString();
            send(new LiteBoard("Wait for other players"));
            server.lobby(this, name);

            while(isActive()){
                if(server.gameHasStarted()){
                   // if(playerTurn) {

                        // liteBoard.setMessage("Insert your move");
                        //  send(liteBoard);
                        String read = readString();
                        read = name + " " + read;
                        notifyObservers(read);


                    //}
                }
            }

            close();

        } catch(IOException e){
            System.err.println(e.getMessage());
            close();
        }
    }

    @Override
    public void update(String message) {
        String[] parts = message.split(" ");

        if(parts[0].equals(name) && parts[1].equals("loses"))
            server.deregisterConnection(this);
        else if(parts[0].equals(name) && parts[1].equals("moves")) {
            playerTurn = true;
            send(new LiteBoard("player " + message));
        } else if (parts[1].equals("moves")) {
            playerTurn = false;
            send(new LiteBoard("player " + message));
        } else send(new LiteBoard("Error: " + message));

    }

    public synchronized void newBoard(LiteBoard board) {
        //TODO Inviare sul socket la board
            setLiteBoard(board);
            String[] parts = board.getMessage().split(" ");

            if(parts[0].equals(name) && parts[1].equals("loses"))
                server.deregisterConnection(this);
            else if(parts[0].equals(name) && parts[1].equals("moves")) {
                playerTurn = true;
                liteBoard.setMessage("player " + board.getMessage());
                send(liteBoard);
            } else if (parts[1].equals("moves")) {
                playerTurn = false;
                liteBoard.setMessage("player " + board.getMessage());
                send(liteBoard);
            } else if (parts[0].equals(name) && parts[1].equals("update")) {
                liteBoard.setMessage("Insert " + board.getMessage());
                send(liteBoard);
            }
            else send(new LiteBoard("Error: " + board.getMessage()));

    }

    public void setLiteBoard(LiteBoard liteBoard) { this.liteBoard = liteBoard; }
}
