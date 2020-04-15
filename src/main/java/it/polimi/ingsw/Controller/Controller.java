package it.polimi.ingsw.Controller;

import it.polimi.ingsw.AthenaException;
import it.polimi.ingsw.Model.Divinity;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class Controller extends Observable implements Observer {

    private FiniteStateMachine fsm;
    private Game game;

    public Controller (Game game) {
        fsm = new FiniteStateMachine();
        fsm.setState(State.start);
        this.game = game;
    }

    /**
     * Parses the input message from the client and call the matching methods
     * it also checks if the client is following the right path
     * @param message == "player action row column"
     */
    public void parseInput(String message) throws IllegalStateException, IllegalArgumentException, AthenaException {

        String[] parts = message.split(" ");
        String name = parts[0];
        String action = parts[1].toLowerCase();
        int row = Integer.parseInt(parts[2]);
        int column = Integer.parseInt(parts[3]);
        int worker = Integer.parseInt(parts[4]);

        if (name.equals(game.getPlayer().getName())) {
            switch (fsm.getState()) {
                case start:
                    if (action.equals("usepower")) fsm.setPath(game.getPlayer().getDivinity());
                    else if (action.equals("default")) fsm.setPath(Divinity.Default);
                    else throw new IllegalArgumentException("Unexpected action: " + action);
                    break;

                case move:
                    if (action.equals("move")) game.move(row, column, worker);
                    break;
                case build:
                    if (action.equals("build")) game.build(row, column, worker);
                    break;
                case superMove:
                    if (action.equals("supermove")) game.useGodPower(row, column, worker);
                    break;
                case superBuild:
                    if (action.equals("superbuild")) game.useGodPower(row, column, worker);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + fsm.getState());
            }
        }
        fsm.nextState();

        if(fsm.getState() == State.endTurn) {
            fsm.setState(State.start);
            game.endTurn();
        }
    }

    @Override
    public void update(String message) throws AthenaException {
        parseInput(message);
    }
}
