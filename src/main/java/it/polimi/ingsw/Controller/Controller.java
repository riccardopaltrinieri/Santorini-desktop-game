package it.polimi.ingsw.Controller;

import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.LiteBoard;

public class Controller extends Observable implements Observer {

    private final FiniteStateMachine fsm;
    private final Game game;
    private State lastAction;
    private boolean undoing;

    /**
     * Constructor of the class, it sets the game to control and create a FSM
     * which check the sequence of actions that the current player must follow
     */
    public Controller (Game game) {
        fsm = new FiniteStateMachine();
        this.game = game;
    }

    /**
     * Parses the input message from the client and calls the matching methods
     * it also checks if the client is following the right path
     * @param message == "player action row column"
     */
    public synchronized void parseInput(String message) {

        try {
            int row, column, worker;
            String[] parts = message.split(" ");
            String name = parts[0];
            InputString action = InputString.valueOf(parts[1].toLowerCase());
            boolean actionExecuted = false;
            UndoHandler undoThread = null;

            // The name is set by the connection so only the current player can execute actions
            if (name.equals(game.getCurrentPlayer().getName())) {


                switch (action) {
                    case usepower -> {
                        // Set the path of fsm as the one of the divinity
                        if (fsm.getState() == State.start)
                            fsm.setPath(game.getCurrentPlayer().getGodPower().getDivinity());
                        String msg = "Insert " + game.getCurrentPlayer().getName() + " wants to use the God Power";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                        actionExecuted = true;
                        lastAction = fsm.getState();
                    }
                    case normal -> {
                        // Set the default path
                        if (fsm.getState() == State.start) fsm.setPath(Divinity.Default);
                        String msg = "Insert " + game.getCurrentPlayer().getName() + " doesn't want to use the God Power";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                        actionExecuted = true;
                        lastAction = fsm.getState();
                    }
                    case placeworker -> {
                        // Place the worker on the map
                        // There isn't a state.placeworker because it happen only once
                        if (fsm.getState() == State.start) fsm.setPath(Divinity.Default);
                        if (fsm.getState() == State.move) fsm.setState(State.build);
                        row = Integer.parseInt(parts[2]) - 1;
                        column = Integer.parseInt(parts[3]) - 1;
                        undoThread = new UndoHandler(game, this, fsm.getState());
                        actionExecuted = game.placeWorker(row, column);
                        lastAction = fsm.getState();
                    }
                    case move -> {
                        // Move the worker
                        row = Integer.parseInt(parts[2]) - 1;
                        column = Integer.parseInt(parts[3]) - 1;
                        worker = Integer.parseInt(parts[4]) - 1;
                        if (fsm.getState() == State.move) {
                            undoThread = new UndoHandler(game, this, fsm.getState());
                            actionExecuted = game.move(row, column, worker);
                        }
                        if (fsm.getState() == State.superMove || fsm.getState() == State.secondTimeState) {
                            undoThread = new UndoHandler(game, this, fsm.getState());
                            actionExecuted = game.useGodPower(row, column, worker);
                        }
                        lastAction = fsm.getState();
                    }
                    case build -> {
                        // Build with the worker
                        row = Integer.parseInt(parts[2]) - 1;
                        column = Integer.parseInt(parts[3]) - 1;
                        worker = Integer.parseInt(parts[4]) - 1;
                        if (fsm.getState() == State.build) {
                            undoThread = new UndoHandler(game, this, fsm.getState());
                            actionExecuted = game.build(row, column, worker);
                        }
                        if (fsm.getState() == State.superBuild || fsm.getState() == State.secondTimeState) {
                            undoThread = new UndoHandler(game, this, fsm.getState());
                            actionExecuted = game.useGodPower(row, column, worker);
                        }
                        lastAction = fsm.getState();
                    }

                    case undo -> {
                        undoing = true;
                        actionExecuted = false;
                        undoThread = new UndoHandler(game, this, State.undo);
                        new Thread(undoThread).start();
                        String msg = "Undo: " + game.getCurrentPlayer().getName() + " undoing";
                        game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                    }

                    case endturn -> {
                        if (fsm.getState() == State.endTurn) {
                            undoing = false;
                            actionExecuted = game.endTurn();
                            lastAction = fsm.getState();
                        }
                    }

                }

                // If something went wrong:
                //   actionExecuted will be false,
                //   the fsm stay in the current state and
                //   the undoThread doesn't start so it can't undo last action
                if (actionExecuted) {
                    undoing = false;
                    fsm.nextState();
                    if (undoThread != null) new Thread(undoThread).start();
                }
            }
            else throw new IllegalArgumentException();

        } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
            game.sendBoard(new LiteBoard("Please, be sure you're using the official software since last message was unexpected or had a wrong format"));
        }
    }

    @Override
    public void update(String message) {
        parseInput(message);
    }
    public void newBoard(LiteBoard board) {
        // Method used only by game to send the board
    }
    public boolean isUndoing() {
        return undoing;
    }
    public void setUndoing(boolean undoing) {
        this.undoing = undoing;
    }
    public State getLastAction() {
        return lastAction;
    }
    public FiniteStateMachine getFsm() {
        return fsm;
    }

}
