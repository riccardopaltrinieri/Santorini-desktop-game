package it.polimi.ingsw.View;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable implements Runnable, Observer {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String name;
    private boolean active;
    private boolean playerTurn;
    private Scanner in2;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
    }

    public Scanner getIn(){
        return this.in;
    }
    private synchronized boolean isActive(){
        return active;
    }


    public void send(String message){
        out.println(message);
        out.flush();
    }

    public String readString(){
        String read = "";
        while (read.isEmpty()) {
            read = (in.nextLine());
        }
        return read;
    }

    public synchronized void closeConnection(){
        send("Connection closed from the server side");
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
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?");
            name = readString();

            server.lobby(this, name);

            while(isActive()){
                if(server.gameHasStarted()){
                    if(playerTurn) {
                        send("Insert your move:");
                        String read = readString();
                        read = name + " " + read;
                        notifyObservers(read);
                    }
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
        playerTurn = parts[0].equals(name) && parts[1].equals("moves");
    }
}
