package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class RemoteView extends Observable implements Observer {
    private Connection connection;
    private Player player;
        public void update (String message){
             System.out.println("Received:" + message);

         }
/*



    public RemoteView (Player player, String opponent, Connection c){
        this.connection = c;
        this.player = player;


    } */

}
