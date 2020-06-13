package it.polimi.ingsw.Controller;

import it.polimi.ingsw.utils.Divinity;

public class FiniteStateMachine {

    private State state;
    private Divinity divinity;
    private boolean again;

    /**
     * constructor of the class, it set the initial state to "start"
     */
    public FiniteStateMachine(){
        this.state = State.start;
        this.again = false;
    }

    /**
     * Increments the state of the FSM following the player's divinity customized path
     * @throws IllegalStateException when the divinity is not recognized
     */
    protected void nextState() throws IllegalStateException {

        if(state == State.endTurn) {
            state = State.start;
            return;
        }

        switch (this.divinity) {

            case Default:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;

            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
                if (state == State.start) state = State.superMove;
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;

            case Artemis:
                if (state == State.start) state = State.superMove;
                else if (state == State.superMove) setStateAfterTwoTimes(State.build);
                else if (state == State.build) state = State.endTurn;
                break;

            case Atlas:
            case Hephaestus:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.superBuild;
                else if (state == State.superBuild) state = State.endTurn;
                break;

            case Demeter:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.superBuild;
                else if (state == State.superBuild) setStateAfterTwoTimes(State.endTurn);
                break;

            case Prometheus:
                if (state == State.start) state = State.build;
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) {
                     // Doesn't call the method setStateAfterTwoTimes because every time
                     // the state change to different ones
                    if (!again) {
                        again = true;
                        state = State.superMove;
                    } else {
                        again = false;
                        state = State.endTurn;
                    }
                }
                break;
        }
    }

    protected void prevState() throws IllegalStateException {

        switch (this.divinity) {

            case Default:
                if (state == State.move) state = State.start;
                else if (state == State.build) state = State.move;
                else if (state == State.endTurn) state = State.build;
                break;

            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
                if (state == State.superMove) state = State.start;
                else if (state == State.build) state = State.superMove;
                else if (state == State.endTurn) state = State.build;
                break;

            case Artemis:
                if (state == State.superMove) resetTwoTimesState(State.start, false);
                else if (state == State.build) resetTwoTimesState(State.superMove, true);
                else if (state == State.endTurn) state = State.build;
                break;

            case Atlas:
            case Hephaestus:
                if (state == State.move) state = State.start;
                else if (state == State.superBuild) state = State.move;
                else if (state == State.endTurn) state = State.superBuild;
                break;

            case Demeter:
                if (state == State.move) state = State.start;
                else if (state == State.superBuild) resetTwoTimesState(State.move, false);
                else if (state == State.endTurn) resetTwoTimesState(State.superBuild, true);
                break;

            case Prometheus:
                if (state == State.superMove) {
                    again = false;
                    state = State.build;
                } else if (state == State.build) {
                    if (again) state = State.superMove;
                    else state = State.start;
                } else if (state == State.endTurn) {
                    again = true;
                    state = State.build;
                }
                break;
        }
    }

//  ************** GETTER AND SETTER **************************

    public State getState() {
        return state;
    }

    protected void setState(State state) {
        this.state = state;
    }

    /**
     * Method used to execute the same action two times without changing the state
     * of the fsm when called for the first time
     */
    private void setStateAfterTwoTimes(State newState) {
        if (!again) {
            again = true;
        } else {
            again = false;
            state = newState;
        }
    }

    protected void resetTwoTimesState(State oldState, boolean toSecondTime) {
        if(toSecondTime) {
            again = true;
            state = oldState;
        } else {
            if(again) again = false;
            else state = oldState;
        }
    }

    protected void setPath(Divinity divinity) {
        this.divinity = divinity;
    }


}
