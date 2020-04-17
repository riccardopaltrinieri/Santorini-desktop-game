package it.polimi.ingsw.View;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.utils.Observable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String name;
    private boolean active;
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
            // qui posso lavorare per giostrale la logica o nella lobby
            send("Welcome! What's your name?");
            name = in.nextLine();

            server.lobby(this, name);

            while(isActive()){
                    if(server.getDivinityPlay1() != null){
                        send("Insert your move:");
                        String read =(name + " " + in.nextLine());
                        notifyObservers(read);
                    }


                /*else {
                    send("Waiting for other players to choose...");

                } */

            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }
}
