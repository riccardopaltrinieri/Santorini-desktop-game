package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.Model.Color.*;

public class Server {
    private static final int PORT= 12345;
    private ServerSocket serverSocket;
    private int numPlayers;

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

    public Connection getConnection (int i){
        return connections.get(i);
    }
    public void setNumPlayers (int i){
        numPlayers=i;
    }
    public int getNumPlayers () { return numPlayers; }


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    public void decideNumberPlayer(Connection c){
            c.send("Number of players?");
            this.setNumPlayers(c.getIn().nextInt());
            //quello che può fare il primo giocatore
            if (this.getNumPlayers() == 3){
                c.send("Choose three divinities. First divinity:");

                this.setDivinity1(c.getIn().next());
                c.send("Second divinity");
                this.setDivinity2(c.getIn().next());
                c.send("Third divinity");
                this.setDivinity3(c.getIn().next());
                c.send("All divinities have been chosen");
            }
            if (this.getNumPlayers() == 2){
                c.send("Choose two divinities. First divinity:");

                this.setDivinity1(c.getIn().next());
                c.send("Second divinity");
                this.setDivinity2(c.getIn().next());
                c.send("All divinities have been chosen");
            }
        }



    public void startDivinity(){
        if ((connections.size()==2)&&(this.getNumPlayers()==2)){
            //the play2 chooses his div
            getConnection(1).send("Choose your Divinity between:" + this.getDivinity1() + this.getDivinity2());
            this.setDivinityPlay2(getConnection(1).getIn().next()); //qui forse c'è lo scanner che funziona
            getConnection(1).send("Your Divinity:"+this.getDivinityPlay2());
            //the play1 chooses his div
            if (this.getDivinityPlay2().equals(this.getDivinity2())){
                        this.getConnection(0).send("Your Divinity" + this.getDivinity1());
                        this.setDivinityPlay1(this.getDivinity1());

                    }
                    else {
                        this.getConnection(0).send("Your Divinity" + this.getDivinity2());
                        this.setDivinityPlay1(this.getDivinity2());
                    }
        }
        if ((connections.size()==3)&&(this.getNumPlayers()==3)) {
            //the play2 chooses his div
            getConnection(1).send("Choose your Divinity between:" + this.getDivinity1() + this.getDivinity2() + this.getDivinity3());
            this.setDivinityPlay2(getConnection(1).getIn().next()); //qui forse c'è lo scanner che funziona
            getConnection(1).send("Your Divinity:" + this.getDivinityPlay2());

            //the play3 chooses his div

            if (getDivinityPlay2().equals(getDivinity1())){ //if the div of play2 is div1 i send div2 and 3 to play 3
                getConnection(2).send("Choose your Divinity between:" + this.getDivinity2() + this.getDivinity3());
                this.setDivinityPlay3(getConnection(2).getIn().next());
                getConnection(2).send("Your Divinity:" + this.getDivinityPlay3());
                //the play1 chooses his div
                if (getDivinityPlay3().equals(getDivinity2())){ //play2=div1 play3=div2 => play1=div3
                    getConnection(0).send("Your Divinity:" + this.getDivinity3());
                    this.setDivinityPlay1(this.getDivinity3());
                }
                else{ //play2=div1 play3=div3 =>play1=div2
                    getConnection(0).send("Your Divinity:" + this.getDivinity2());
                    this.setDivinityPlay1(this.getDivinity2());
                }
            }

            // play3 chooses his div
            if (getDivinityPlay2().equals(getDivinity2())){ //if the div of play2 is div2 i send div1 and 3 to play 3
                getConnection(2).send("Choose your Divinity between:" + this.getDivinity1() + this.getDivinity3());
                this.setDivinityPlay3(getConnection(2).getIn().next());
                getConnection(2).send("Your Divinity:" + this.getDivinityPlay3());

                if (getDivinityPlay3().equals(getDivinity1())){ //play2=div2 play3=div1 =>play=div3
                    getConnection(0).send("Your Divinity:" + this.getDivinity3());
                    this.setDivinityPlay1(this.getDivinity3());
                }
                else{ //play2=div2 play3=div3 =>play1=div1
                    getConnection(0).send("Your Divinity:" + this.getDivinity1());
                    this.setDivinityPlay1(this.getDivinity1());
                }
            }

            // play3 chooses his div

            if (getDivinityPlay2().equals(getDivinity3())){ //if the div2=div3 i send div1 and 2 to play 3
                getConnection(2).send("Choose your Divinity between:" + this.getDivinity1() + this.getDivinity2());
                this.setDivinityPlay3(getConnection(2).getIn().next());
                getConnection(2).send("Your Divinity:" + this.getDivinityPlay3());

                if (getDivinityPlay3().equals(getDivinity1())){ //play2=div3 play3=div1 =>play1=div2
                    getConnection(0).send("Your Divinity:" + this.getDivinity2());
                    this.setDivinityPlay1(this.getDivinity2());
                }
                else{ //play2=div3 play3=div2 =>play1=div1
                    getConnection(0).send("Your Divinity:" + this.getDivinity1());
                    this.setDivinityPlay1(this.getDivinity1());
                }
            }
        }
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
        if (waitingConnection.size()==1){
            this.decideNumberPlayer(connections.get(0)); //decide num of player

        }
        if((waitingConnection.size()==2)&&(numPlayers==2)){
            startDivinity();
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));

            //qui Remote View

            Game game = new Game();
            Controller controller = new Controller(game);
            Player player1 = new Player(keys.get(0), Red , game);
            Player player2 = new Player(keys.get(1), Yellow, game);
            player1.setDivinity(Divinity.valueOf(this.getDivinityPlay1()));
            player2.setDivinity(Divinity.valueOf(this.getDivinityPlay2()));
            //qui ci vanno gli observer
            connections.get(0).addObserver(controller);
            connections.get(1).addObserver(controller);

            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }

        if ((waitingConnection.size()==3)&&(numPlayers==3)){
            startDivinity();
            List<String> keys = new ArrayList<>(waitingConnection.keySet()); // in questo modo ho i nomi dei giocatori in array
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            Connection c3 = waitingConnection.get(keys.get(2));

            //parte RemoteView

            Game game = new Game();
            Controller controller = new Controller(game);
            Player player1 = new Player(keys.get(0), Red , game);
            Player player2 = new Player(keys.get(1), Yellow, game);
            Player player3 = new Player(keys.get(2), Green, game);
            player1.setDivinity(Divinity.valueOf(this.getDivinityPlay1()));
            player2.setDivinity(Divinity.valueOf(this.getDivinityPlay2()));
            player3.setDivinity(Divinity.valueOf(this.getDivinityPlay3()));

            // parte osservatori

            connections.get(0).addObserver(controller);
            connections.get(1).addObserver(controller);
            connections.get(2).addObserver(controller);

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
