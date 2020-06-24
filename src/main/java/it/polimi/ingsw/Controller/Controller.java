package it.polimi.ingsw.Controller;

import it.polimi.ingsw.utils.Divinity;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.InputString;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class Controller extends Observable implements Observer {

    private final FiniteStateMachine fsm;
    private final Game game;
    private State lastAction;
    private boolean undoing;

    /**
     * Constructor of the class, it sets the game to control and create the FSM
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
    public void parseInput(String message) throws IllegalStateException, IllegalArgumentException {

        int row, column, worker;
        String[] parts = message.split(" ");
        String name = parts[0];
        InputString action = InputString.valueOf(parts[1].toLowerCase());
        boolean actionExecuted = false;

        // The name is set by the connection so only the current player can execute actions
        if (name.equals(game.getCurrentPlayer().getName())) {


            switch (action) {
                case usepower -> {
                    // Set the path of fsm as the one of the divinity
                    if (fsm.getState() == State.start) fsm.setPath(game.getCurrentPlayer().getGodPower().getDivinity());
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
                    actionExecuted = game.placeWorker(row, column);
                    lastAction = fsm.getState();
                }
                case move -> {
                    // Move the worker
                    row = Integer.parseInt(parts[2]) - 1;
                    column = Integer.parseInt(parts[3]) - 1;
                    worker = Integer.parseInt(parts[4]) - 1;
                    UndoChecker undoThread = new UndoChecker(game, this, fsm.getState());
                    if (fsm.getState() == State.move) {
                        undoThread.remember(worker);
                        actionExecuted = game.move(row, column, worker);
                        undoThread.start();
                    }
                    if (fsm.getState() == State.superMove) {
                        undoThread.remember(worker, row, column);
                        actionExecuted = game.useGodPower(row, column, worker);
                        undoThread.start();
                    }
                    lastAction = fsm.getState();
                }
                case build -> {
                    // Build with the worker
                    row = Integer.parseInt(parts[2]) - 1;
                    column = Integer.parseInt(parts[3]) - 1;
                    worker = Integer.parseInt(parts[4]) - 1;
                    UndoChecker undoThread = new UndoChecker(game, this, fsm.getState());
                    if (fsm.getState() == State.build) {
                        undoThread.remember(row, column);
                        actionExecuted = game.build(row, column, worker);
                        undoThread.start();
                    }
                    if (fsm.getState() == State.superBuild) {
                        undoThread.remember(worker, row, column);
                        actionExecuted = game.useGodPower(row, column, worker);
                        undoThread.start();
                    }
                    lastAction = fsm.getState();
                }

                case undo -> {
                    undoing = true;
                    actionExecuted = false;
                    UndoChecker undoThread = new UndoChecker(game, this, State.undo);
                    undoThread.start();
                    String msg = "Undo: " + game.getCurrentPlayer().getName() + " undoing";
                    game.sendBoard(new LiteBoard(msg, game.getBoard(), game));
                }

                case endturn -> {
                    undoing = false;
                    actionExecuted = game.endTurn();
                    lastAction = fsm.getState();
                }

            }

            // If something went wrong actionExecuted will be false and the fsm stay in the current state
            if(actionExecuted) {
                undoing = false;
                fsm.nextState();
            }
        }
    }

    /**
     * Observer method:
     * receives a message from the observable and parse it with the parseInput method
     */
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
