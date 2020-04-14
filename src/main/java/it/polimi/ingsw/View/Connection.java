package it.polimi.ingsw.View;

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
    private boolean active = true;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
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
            if (server.getsizeconnections() == 1){ //questa cosa è da controllare
                send("Number of players?");
                server.setNumplayers(in.nextInt());
                //quello che può fare il primo giocatore
                send("Choose three divinities:");
                send("First divinity:");
                server.setDivinity1(in.next());
                send("Second divinity");
                server.setDivinity2(in.next());
                send("Third divinity");
                server.setDivinity3(in.next());
                send("All divinities have been chosen. Choose yours between: "+server.getDivinity1() + server.getDivinity2() + server.getDivinity3());
                server.setDivinityPlay1(in.next());
                send("Your Divinity:"+server.getDivinityPlay1());
            }

            if (server.getsizeconnections() == 2){

                    if (server.getDivinityPlay1().equals(server.getDivinity1())) {
                        send("Choose your Divinity between:" + server.getDivinity2() + server.getDivinity3());
                        server.setDivinityPlay2(in.next());
                    }
                    if(server.getDivinityPlay1().equals(server.getDivinity2())) {
                        send("Choose your Divinity between:" + server.getDivinity1() + server.getDivinity3());
                        server.setDivinityPlay2(in.next());
                    }
                    if(server.getDivinityPlay1().equals((server.getDivinity3()))) {
                            send("Choose your Divinity between:" + server.getDivinity1() + server.getDivinity2());
                            server.setDivinityPlay2(in.next());
                    }
                }

            if (server.getsizeconnections() == 3) {
                if (!(server.getDivinity1().equals(server.getDivinityPlay1())) && !(server.getDivinity1().equals(server.getDivinityPlay2()))) {
                    send("Choose your Divinity between:" + server.getDivinity1());
                    server.setDivinityPlay3(in.next());
                }
                if (!(server.getDivinity2().equals(server.getDivinityPlay1())) && !(server.getDivinity2().equals(server.getDivinityPlay2()))) {
                    send("Choose your Divinity between:" + server.getDivinity2());
                    server.setDivinityPlay3(in.next());
                }
                if (!(server.getDivinity3().equals(server.getDivinityPlay1())) && !(server.getDivinity3().equals(server.getDivinityPlay2()))) {
                    send("Choose your Divinity between:" + server.getDivinity3());
                    server.setDivinityPlay3(in.next());
                }
            }

            server.lobby(this, name);
            while(isActive()){
                String read = in.nextLine();
                // notify(read);
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }
}
