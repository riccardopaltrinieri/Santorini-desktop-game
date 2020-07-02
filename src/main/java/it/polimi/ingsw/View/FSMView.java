package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.Divinity;

public class FSMView {

    private State state;
    private State lastState;
    private Divinity divinity;
    private boolean again;
    private String godName;

    /**
     * constructor of the class, it set the initial state to "start"
     */
    public FSMView(){
        this.state = State.placeworker;
        this.again = false;
        this.divinity = Divinity.Default;
    }

    /**
     * Return the string to show to the player according to the fsm state for the Cli
     */
    public String getStateStringCLI() {

        return switch (state) {
            case placeworker -> "Place your worker on the map: (write 'placeworker [row] [column]')";
            case start -> "Are you going to use the " + godName + " power or going with the normal turn? (usepower/normal)";
            case worker -> "Choose the worker that you want to move and build with: (write 'worker 1' or 'worker 2]')";
            case move -> "Where do you want to move? (write 'move [row] [column]')";
            case build -> "Where do you want to build? (write 'build [row] [column]')";
            case endTurn -> "Write 'endturn' to confirm your actions: ";
        };
    }

    /**
     * Return the string to show to the player according to the fsm state for the GUI
     */
    public String getStateStringGUI() {

        return switch (state) {
            case placeworker -> "Place your worker on the map: (select a free cell on the board')";
            case start -> "Are you going to use the " + godName + " power or going with the normal turn?";
            case worker -> "Choose the worker that you want to move and build with: (select one of your worker)";
            case move -> "Where do you want to move? (select a free cell reachable from your worker)";
            case build -> "Where do you want to build? (select a cell where your can build)";
            case endTurn -> "Click the \"endturn\" button to confirm your actions";
        };
    }

    /**
     * Increments the state of the FSM following the player's divinity customized path
     * @throws IllegalStateException when the divinity is not recognized
     */
    protected void nextState() throws IllegalStateException {

        lastState = state;

        if (state == State.placeworker) {
            setStateAfterTwoTimes(State.endTurn);
            return;
        }

        if (state == State.endTurn) {
            setState(State.start);
            return;
        }

        switch (divinity) {

            case Default:
            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
            case Atlas:
            case Hephaestus:
                if (state == State.start) setState(State.worker);
                else if (state == State.worker) setState(State.move);
                else if (state == State.move) setState(State.build);
                else if (state == State.build) setState(State.endTurn);
                break;

            case Artemis:
                if (state == State.start) setState(State.worker);
                else if (state == State.worker) setState(State.move);
                else if (state == State.move) setStateAfterTwoTimes(State.build);
                else if (state == State.build) setState(State.endTurn);
                break;

            case Demeter:
                if (state == State.start) setState(State.worker);
                else if (state == State.worker) setState(State.move);
                else if (state == State.move) setState(State.build);
                else if (state == State.build) setStateAfterTwoTimes(State.endTurn);
                break;

            case Prometheus:
                if (state == State.start) setState(State.worker);
                else if (state == State.worker) setState(State.build);
                else if (state == State.move) setState(State.build);
                else if (state == State.build) {
                    if (!again) {
                        again = true;
                        setState(State.move);
                    } else {
                        again = false;
                        setState(State.endTurn);
                    }
                }
                break;
        }
    }

    protected void prevState() {

        if(lastState == State.placeworker) {
            setState(lastState);
            again = !again;
        }

        switch (this.divinity) {

            case Default:
            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
            case Atlas:
            case Hephaestus:
                if (state == State.worker) setState(State.start);
                else if (state == State.move) setState(State.worker);
                else if (state == State.build) setState(State.move);
                else if (state == State.endTurn) setState(State.build);
                break;

            case Artemis:
                if (state == State.worker) setState(State.start);
                else if (state == State.move) resetTwoTimesState(State.worker, false);
                else if (state == State.build) resetTwoTimesState(State.move, true);
                else if (state == State.endTurn) setState(State.build);
                break;

            case Demeter:
                if (state == State.worker) setState(State.start);
                else if (state == State.move) setState(State.worker);
                else if (state == State.build) resetTwoTimesState(State.move, false);
                else if (state == State.endTurn) resetTwoTimesState(State.build, true);
                break;

            case Prometheus:
                if (state == State.worker) setState(State.start);
                else if (state == State.build) {
                    if (!again) {
                        state = State.worker;
                    } else {
                        state = State.move;
                    }
                }
                else if (state == State.move) {
                    setState(State.build);
                    again = false;
                }
                else if (state == State.endTurn) {
                    again = true;
                    setState(State.build);
                }
                break;
        }
    }

    private void setStateAfterTwoTimes(State newState) {
        if (!again) {
            again = true;
        } else {
            again = false;
            state = newState;
        }
    }

    private void resetTwoTimesState(State oldState, boolean toSecondTime) {
        if(toSecondTime) {
            again = true;
            state = oldState;
        } else {
            if(again) again = false;
            else state = oldState;
        }
    }

//  ************** GETTER AND SETTER **************************

    public State getState() {
        return state;
    }

    public State getLastState() {
        return lastState;
    }

    protected void setState(State state) {
        this.state = state;
    }

    protected void setPath(Divinity divinity) {
        this.divinity = divinity;
    }

    protected Divinity getPath() {
        return divinity;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }
}
