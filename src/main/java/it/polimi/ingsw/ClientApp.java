package it.polimi.ingsw;

import it.polimi.ingsw.View.CLInterface;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        CLInterface CLI = new CLInterface("127.0.0.1", 12345);
        try{
            CLI.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
