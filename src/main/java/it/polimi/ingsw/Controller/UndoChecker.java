package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.CareTaker;

import static java.lang.Thread.sleep;

class UndoChecker extends CareTaker implements Runnable{

    private final Game game;
    private final Controller controller;
    private final State state;
    private final Player player;

    public UndoChecker(Game game, Controller controller, State state) {
        this.game = game;
        this.controller = controller;
        this.state = state;
        this.player = game.getCurrentPlayer();
        save(game);
    }

    @Override
    public void run() {
        try {

            sleep(5000);

            if (controller.isUndoing() && player == game.getCurrentPlayer()) {

                if(state != it.polimi.ingsw.Controller.State.undo) {
                    // Check if the player didn't make any other moves
                    if (controller.getLastAction() == state) {

                        // Restore last state of the game
                        undo(game);

                        controller.getFsm().prevState();
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " undid last action";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }

                } else {

                    sleep(3000);
                    // If 8 seconds from the request passed and the undo flag is still true it means
                    // that the previous thread didn't worked
                    if(controller.isUndoing()) {
                        controller.setUndoing(false);
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " cannot undo last action";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
