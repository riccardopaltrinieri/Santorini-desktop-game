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

    /**
     * Constructor used to create a connection
     * @param socket is the socket created by Server
     * @param server is the Server of game
     */
    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
    }

    public ObjectInputStream getIn(){
        return this.in;
    }

    /**
     * @return if the connection is still active
     */
    private synchronized boolean isActive(){
        return active;
    }


    /**
     * Method that send the message and the board to each Client from Game.
     * @param board this is the LiteBoard that we send to Client. It can be a message or a message+board+game
     */
    public void send(LiteBoard board){
        try {
            out.reset();
            out.writeObject(board);
            out.flush();
        }  catch (IOException e) {
            System.out.println("Socket closed");
        }
    }

    /**
     * Read the input of client
     * @return the input of Client
     * @throws IOException if the socket is close
     */
    public String readString() throws IOException {
        String read = "";
        while (read.isBlank()) {
            read = (in.readUTF());
        }
        return read;
    }

    /**
     * We the connection ends we use this method to close the socket
     */
    public synchronized void closeConnection(){
        send( new LiteBoard("Connection closed from the server side"));
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    /**
     * Deregister the connection in server and print the the message "Done" in Server
     */
    private void close(){
        System.out.println("Deregistering client: " + name);
        server.deregisterConnection(this);
        closeConnection();
        System.out.println("Done!");
    }

    /**
     * Create the ObjectOutputStream and ObjectInputStream. When the connection isActive read the input of each client.
     */
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
                close();
                server.endGame();
            }
            System.err.println("Connection with " + name + " not more active");
        }
    }

    /**
     * Receive the board from game and send it to each Client. After check if there is a winner or a loser.
     * @param board is the board received from the game.
     */
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
