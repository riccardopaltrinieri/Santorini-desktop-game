package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.LiteBoard;
import it.polimi.ingsw.utils.InputString;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class Controller extends Observable implements Observer {

    private FiniteStateMachine fsm;
    private Game game;

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

        if (name.equals(game.getCurrentPlayer().getName())) {

            switch (action) {
                case usepower:
                    if (fsm.getState() == State.start) fsm.setPath(game.getCurrentPlayer().getDivinity());
                    break;

                case normal:
                    if (fsm.getState() == State.start) fsm.setPath(Divinity.Default);
                    break;

                case placeworker:
                    if (fsm.getState() == State.start) fsm.setPath(Divinity.Default);
                    if (fsm.getState() == State.move) fsm.setState(State.build);
                    row = Integer.parseInt(parts[2]);
                    column = Integer.parseInt(parts[3]);
                    actionExecuted = game.placeWorker(row, column);
                    break;

                case move:
                    row = Integer.parseInt(parts[2]);
                    column = Integer.parseInt(parts[3]);
                    worker = Integer.parseInt(parts[4]);
                    if (fsm.getState() == State.move) actionExecuted = game.move(row, column, worker);
                    break;

                case build:
                    row = Integer.parseInt(parts[2]);
                    column = Integer.parseInt(parts[3]);
                    worker = Integer.parseInt(parts[4]);
                    if (fsm.getState() == State.build) actionExecuted = game.build(row, column, worker);
                    break;

                case supermove:
                case superbuild:
                    row = Integer.parseInt(parts[2]);
                    column = Integer.parseInt(parts[3]);
                    worker = Integer.parseInt(parts[4]);
                    if (fsm.getState() == State.superMove || fsm.getState() == State.superBuild) actionExecuted = game.useGodPower(row, column, worker);
                    break;

            }

            if(actionExecuted) fsm.nextState();

            if (fsm.getState() == State.endTurn) {
                fsm.setState(State.start);
                game.endTurn();
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
    }
}
