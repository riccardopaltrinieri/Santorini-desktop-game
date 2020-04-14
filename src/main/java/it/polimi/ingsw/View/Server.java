package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT= 12345;
    private ServerSocket serverSocket;
    private int numplayers;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections = new ArrayList<>();
    private Map<String, Connection> waitingConnection = new HashMap<>();
    private Map<Connection, Connection> playingConnection = new HashMap<>();
    private Map<Connection,Connection> playingConnection3players = new HashMap<>();
    private String divinity1;
    private String divinity2;
    private String divinity3;
    private String divinityPlay1;
    private String divinityPlay2;
    private String divinityPlay3;

    public int getsizeconnections (){
        return connections.size();
    }
    public void setNumplayers (int i){
        numplayers=i;
    }


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        if(playingConnection.size() == 3) {
            for (Connection conn : connections) {
                if (c.equals(playingConnection.get(conn))) {
                    playingConnection.remove(conn);
                }
            }
            for (Connection conn : connections) {
                if (c.equals(playingConnection.get(conn))) {
                    playingConnection3players.remove(conn);
                }
            }
            playingConnection.remove(c); //rimuovo la prima connessione dalla prima hash map
            playingConnection3players.remove(c);
            Connection toadd = connections.get(0);
            if (playingConnection.containsKey(toadd)){
                playingConnection.put(connections.get(1),toadd);
            }
            else {
                playingConnection.put(toadd,connections.get(1));
            }
            playingConnection3players.clear();
        }
        if(playingConnection.size() == 2){
            Connection opponent = playingConnection.get(c);
            if (opponent!= null){
                opponent.closeConnection();
                playingConnection.remove(c);
                playingConnection.remove(opponent);
            }
        }
        connections.remove(c); //messo qui perchè altrimenti con 3 giocatori non ho il riferimento alla connessione eliminata in foreach
    }

    public synchronized void lobby(Connection c, String name){
        waitingConnection.put(name, c);
        if((waitingConnection.size()==2)&&(numplayers==2)){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            //qui Remote View
            Game game = new Game();
            Controller controller = new Controller(game);
            //qui ci vanno gli observer
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }

        if ((waitingConnection.size()==3)&&(numplayers==3)){
            List<String> keys = new ArrayList<>(waitingConnection.keySet()); // in questo modo ho i nomi dei giocatori in array
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            Connection c3 = waitingConnection.get(keys.get(2));
            //parte RemoteView
            Game game = new Game();
            Controller controller = new Controller(game);
            // parte osservatori
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c3);
            playingConnection.put(c3, c1);
            playingConnection3players.put(c1, c3);
            playingConnection3players.put(c2, c1);
            playingConnection3players.put(c3, c2);
            waitingConnection.clear();
        }


    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        System.out.println("Server listening on port: " + PORT);
        //noinspection InfiniteLoopStatement
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

    public void setDivinity1(String divinity1) {
        this.divinity1 = divinity1;
    }

    public String getDivinity1() {
        return divinity1;
    }

    public String getDivinity2() {
        return divinity2;
    }

    public void setDivinity2(String divinity2) {
        this.divinity2 = divinity2;
    }

    public String getDivinity3() {
        return divinity3;
    }

    public void setDivinity3(String divinity3) {
        this.divinity3 = divinity3;
    }

    public String getDivinityPlay1() {
        return divinityPlay1;
    }

    public void setDivinityPlay1(String divinityPlay1) {
        this.divinityPlay1 = divinityPlay1;
    }

    public String getDivinityPlay2() {
        return divinityPlay2;
    }

    public void setDivinityPlay2(String divinityPlay2) {
        this.divinityPlay2 = divinityPlay2;
    }

    public String getDivinityPlay3() {
        return divinityPlay3;
    }

    public void setDivinityPlay3(String divinityPlay3) {
        this.divinityPlay3 = divinityPlay3;
    }
}
