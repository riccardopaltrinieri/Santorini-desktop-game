package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.LiteBoard;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.utils.Color.*;

public class Server implements Runnable {

    private static int PORT;

    private final ServerSocket serverSocket;
    private int numPlayers;

    private final ExecutorService executor = Executors.newFixedThreadPool(128);

    private final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> waitingConnection = new HashMap<>();
    private final Map<String, Connection> playingConnection = new HashMap<>();
    private final ArrayList<String> divinities = new ArrayList<>();
    private final Map<String, String> divinityPlayer = new HashMap<>();
    private String firstPlayer;
    private String secondPlayer;
    private String thirdPlayer;
    private boolean startGame = false;


    public Server(int port) throws IOException {
        PORT = port;
        serverSocket = new ServerSocket(PORT);
    }

    /**
     * This is the run method of server. This is active all the time and accept the socket of client.
     * After create a thread for Connection of each client.
     */
    public void run() {
        System.out.println("Server listening on port: " + PORT);
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e) {
                System.err.println("Connection error!");
            }
        }
    }

    /**
     * When a client connects itself to Server, add it in connections
     * @param c is the connection
     */
    //Register connection
    private void registerConnection(Connection c) {
        connections.add(c);
    }

    /**
     * When a connection ends or the third player loses we use the deregister
     * @param c is the connection
     */
    public synchronized void unregisterConnection(Connection c) {
        if (playingConnection.size() == 3) {
            connections.remove(c);
            numPlayers = 2;

        } else if (playingConnection.size() == 2) {

            playingConnection.clear();
            connections.remove(c);
        }
    }

    /**
     * First player decides the number of player and divinities
     * @param c is the connection
     */
    public void decideNumberPlayerAndDiv(Connection c) {
        try {
            c.send(new LiteBoard("Decide the number of players: [2 or 3]"));
            setNumPlayers(Integer.parseInt(c.getIn().readUTF()));
            c.send(new LiteBoard("Choose the first divinity:"));
            divinities.add(c.getIn().readUTF());
            c.send(new LiteBoard("Choose the second divinity: "));
            divinities.add(c.getIn().readUTF());
            if (this.getNumPlayers() == 3) {
                c.send(new LiteBoard("Choose the third divinity: "));
                divinities.add(c.getIn().readUTF());
            }
            c.send(new LiteBoard("All the divinities have been chosen, "));
            c.send(new LiteBoard("now the other players will choose between them."));
        } catch (IOException e) {
            System.out.println("Client has disconnected while choosing the divinities");
            for (Connection conn : connections) {
                conn.send(new LiteBoard("First client has disconnected while choosing the divinities"));
            }
            endGame();
        }

    }

    /**
     * Each player chooses his Divinity
     */
    public void startDivinity() {
        try {
            StringBuilder choices = new StringBuilder("Choose your Divinity between: ");
            String tempDivinity;
            for (String god : divinities)  choices.append(god).append(' ');

            waitingConnection.get(secondPlayer).send(new LiteBoard(choices.toString()));
            tempDivinity = waitingConnection.get(secondPlayer).getIn().readUTF();
            divinityPlayer.put(secondPlayer, tempDivinity);
            waitingConnection.get(secondPlayer).send(new LiteBoard("Your Divinity: " + tempDivinity));
            divinities.remove(tempDivinity);

            if (numPlayers == 3) {
                choices = new StringBuilder("Choose your Divinity between: ");

                for (String god : divinities)  choices.append(god).append(' ');

                waitingConnection.get(thirdPlayer).send(new LiteBoard(choices.toString()));
                tempDivinity = (waitingConnection.get(thirdPlayer).getIn().readUTF());
                divinityPlayer.put(thirdPlayer, tempDivinity);
                waitingConnection.get(thirdPlayer).send(new LiteBoard("Your Divinity: " + tempDivinity));
                divinities.remove(tempDivinity);
            }

            divinityPlayer.put(firstPlayer, (divinities.get(0)));
            waitingConnection.get(firstPlayer).send(new LiteBoard("Your Divinity: " + divinities.get(0)));
            divinities.clear();

        } catch (IOException e) {
            for (Connection conn : connections) {
                conn.send(new LiteBoard("Client has disconnected while choosing the divinities"));
            }
            endGame();
        }

    }


    /**
     * This is the lobby of the game, in this there are all initial moves
     * @param c is the connection
     * @param name is the name of client
     */
    public synchronized void lobby(Connection c, String name) {
        waitingConnection.put(name, c);
        System.out.println("New client: " + name);
        if (waitingConnection.size() == 1) {
            firstPlayer = name;
            decideNumberPlayerAndDiv(c);
        }
        else if (waitingConnection.size() == 2) secondPlayer = name;
        else if (waitingConnection.size() == 3) thirdPlayer = name;

        c.send(new LiteBoard("Waiting for other players..."));

        if(waitingConnection.size() == numPlayers) {

            startDivinity();
            chooseFirstPlayer(waitingConnection.get(firstPlayer));

            Connection c1 = waitingConnection.get(firstPlayer);
            Connection c2 = waitingConnection.get(secondPlayer);

            Game game = new Game();
            game.setNumPlayer(numPlayers);
            Controller controller = new Controller(game);
            Player player1 = new Player(firstPlayer, White, game);
            Player player2 = new Player(secondPlayer, Purple, game);
            player1.setGodPower(divinityPlayer.get(firstPlayer));
            player2.setGodPower(divinityPlayer.get(secondPlayer));

            game.addObserver(c1);
            game.addObserver(c2);
            c1.addObserver(controller);
            c2.addObserver(controller);

            ArrayList<String> startingMessages = new ArrayList<>();
            startingMessages.add("Start " + player1.getName() + " " + player1.getColor() + " " + player1.getGodPower().getDivinity());
            startingMessages.add("Start " + player2.getName() + " " + player2.getColor() + " " + player2.getGodPower().getDivinity());

            if (numPlayers == 3) {
                Connection c3 = waitingConnection.get(thirdPlayer);
                Player player3 = new Player(thirdPlayer, Red, game);
                player3.setGodPower(divinityPlayer.get(thirdPlayer));

                game.addObserver(c3);
                c3.addObserver(controller);

                startingMessages.add("Start " + player3.getName() + " " + player3.getColor() + " " + player3.getGodPower().getDivinity());
            }
            playingConnection.putAll(waitingConnection);

            // Send to the sockets the players' names and their color, then start the game with the update
            for (String message : startingMessages) game.sendBoard(new LiteBoard(message));
            game.sendBoard(new LiteBoard("Insert " + game.getCurrentPlayer().getName() + " update", game.getBoard(), game));

            divinityPlayer.clear();
            waitingConnection.clear();
            startGame = true;
        }
    }

    /**
     * In this method the first connection choose the first player
     * @param c is the first connection
     */
    private void chooseFirstPlayer(Connection c) {
        List<String> names = new ArrayList<>(waitingConnection.keySet());
        if (numPlayers == 3)
            c.send(new LiteBoard("You choose the first player between: " + names.get(0) + " " + names.get(1) + " " + names.get(2)));
        if (numPlayers == 2)
            c.send(new LiteBoard("You choose the first player between: " + names.get(0) + " " + names.get(1)));
        checkName(c);

        }


    public boolean gameHasStarted() {
        return startGame;
    }

    public void setNumPlayers(int i) {
        numPlayers = i;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Check if the name there is in the waitingConnection or not. If there isn't the client has to reinsert it.
     * @param c is the first connection
     */
    private void checkName(Connection c) {

        try {
            while (true) {

                String firstChosen = (c.getIn().readUTF());

                if (waitingConnection.containsKey(firstChosen)) {
                    if (numPlayers == 2 && firstChosen.equals(secondPlayer)) {
                        secondPlayer = firstPlayer;
                        firstPlayer = firstChosen;
                    } else if (numPlayers == 3) {
                        if (firstChosen.equals(secondPlayer)) {
                            secondPlayer = thirdPlayer;
                            thirdPlayer = firstPlayer;
                            firstPlayer = firstChosen;
                        }
                        if (firstChosen.equals(thirdPlayer)) {
                            thirdPlayer = secondPlayer;
                            secondPlayer = firstPlayer;
                            firstPlayer = firstChosen;
                        }
                    }
                    return;
                }
                else
                    c.send(new LiteBoard("Wrong name, reinsert it.. "));
            }
        } catch (IOException e) {
            System.out.println("Client has disconnected while choosing first player");
            for (Connection conn : connections) {
                conn.send(new LiteBoard("First client has disconnected while choosing first player"));
                conn.close();
            }
        }
    }


    /**
     * Check if the name has already taken
     * @param c is the connection of each player
     */
    public void notEqualsName(Connection c) {
        try{
            boolean taken;
            while(true) {
                taken = false;
                String name = c.readString();
                for (Connection connect: connections) {
                    if (name.equals(connect.getName())) {
                        taken = true;
                        break;
                    }
                }
                    if ((taken)) c.send(new LiteBoard("This name is already taken, choose another name"));
                    else {
                        c.setName(name);
                        if (!waitingConnection.isEmpty()) c.send(new LiteBoard("Waiting the chooses of the Start Player and the start of game"));

                        return;
                    }
                }


        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method called when the the read() on the socket times out. It send a message to other players
     * and close all the other connections.
     * @param name of the player disconnected
     */
    public void timeout(String name) {
        for (Connection conn: connections) {
            conn.send(new LiteBoard("Timeout: " + name + "took too much time to decide. Game over."));
            conn.close();
        }
    }

    /**
     * This method ends the game and close all the connection
     */
    public synchronized void endGame() {
        for (Connection conn : connections) {
            conn.send(new LiteBoard("Client disconnected: the game ends without a winner"));
            conn.close();
        }
        waitingConnection.clear();
        connections.clear();
        playingConnection.clear();
        divinities.clear();
        divinityPlayer.clear();
    }
}


