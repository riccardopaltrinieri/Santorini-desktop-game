package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Cell;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.LiteBoard;

public class UndoChecker extends Thread implements Runnable {

    private final Game game;
    private final Controller controller;
    private final it.polimi.ingsw.Controller.State state;
    private final Player player;
    private int worker;
    private Cell oldPosition;
    private Cell building;

    public UndoChecker(Game game, Controller controller, it.polimi.ingsw.Controller.State state) {
        this.game = game;
        this.controller = controller;
        this.state = state;
        this.player = game.getCurrentPlayer();
    }

    @Override
    public void run() {
        try {

            sleep(5000);
            System.out.println("thread awakened: " + state + " " + this);

            if (controller.isUndoing()) {
                switch (state) {
                    case move -> {
                        player.getWorker(worker).setPosition(oldPosition);
                        controller.setUndoing(false);
                        controller.getFsm().prevState();
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " undid last action";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }
                    case build -> {
                        building.setLevel(building.getLevel() - 1);
                        controller.setUndoing(false);
                        controller.getFsm().prevState();
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " undid last action";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }
                    case superMove, superBuild -> {
                        player.getGodPower().undo(player, oldPosition, worker, building);
                        controller.setUndoing(false);
                        controller.getFsm().prevState();
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " undid last action";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }
                    case undo -> {
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
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void remember(int worker) {
        this.worker = worker;
        this.oldPosition = game.getCurrentPlayer().getWorker(worker).getPosition();
    }
    public void remember(int row, int column) {
        this.building = game.getBoard().getCell(row, column);
    }
    public void remember(int worker, int row, int column) {
        this.worker = worker;
        this.oldPosition = game.getCurrentPlayer().getWorker(worker).getPosition();
        this.building = game.getBoard().getCell(row, column);
    }
}
