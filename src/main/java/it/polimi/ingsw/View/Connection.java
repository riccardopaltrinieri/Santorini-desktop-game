package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Observable implements Runnable, Observer {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final Server server;
    private String name;
    private boolean active;

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
            System.out.println("Socket closed");
        }
    }

    public String readString() throws IOException {
        String read = "";
        while (read.isEmpty()) {
            read = (in.readUTF());
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
        System.out.println("Deregistering client: " + name);
        server.deregisterConnection(this);
        closeConnection();
        System.out.println("Done!");
    }

    @Override
    public void run() {

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(360000);

            send( new LiteBoard("Welcome! What's your name?"));
            server.notEqualsName(this);
            server.lobby(this, name);

            while(isActive()){
                if(server.gameHasStarted()){
                    String read = readString();
                    read = name + " " + read;
                    notifyObservers(read);
                }
            }

        } catch(IOException e){
            if(isActive()) {
                send(new LiteBoard("You took to much time to answer, you lose.."));
                server.endGame();
                close();
            }
            System.err.println("Connection with " + name + " not more active");
        }
    }

    public void newBoard(LiteBoard board) {
        String[] parts = board.getMessage().split(" ");
        send(board);
        if ((parts[1].equals(name) && parts[2].equals("loses"))
                || parts[2].equals("wins")) close();
    }

    public String getName(){ return name; }

    @Override
    public void update(String message) {
        // Method used only by the controller
    }

    public void setName(String n) { name = n; }
}
