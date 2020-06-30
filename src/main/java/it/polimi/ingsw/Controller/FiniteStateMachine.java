package it.polimi.ingsw.Controller;

import it.polimi.ingsw.utils.Divinity;

class FiniteStateMachine {

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
            setState(State.start);
            return;
        }

        switch (this.divinity) {

            case Default:
                if (state == State.start) setState(State.move);
                else if (state == State.move) setState(State.build);
                else if (state == State.build)  setState(State.endTurn);
                break;

            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
                if (state == State.start)  setState(State.superMove);
                else if (state == State.superMove)  setState(State.build);
                else if (state == State.build)  setState(State.endTurn);
                break;

            case Artemis:
                if (state == State.start)  setState(State.superMove);
                else if (state == State.superMove)  setState(State.secondTimeState);
                else if (state == State.secondTimeState)  setState(State.build);
                else if (state == State.build)  setState(State.endTurn);
                break;

            case Atlas:
            case Hephaestus:
                if (state == State.start)  setState(State.move);
                else if (state == State.move)  setState(State.superBuild);
                else if (state == State.superBuild)  setState(State.endTurn);
                break;

            case Demeter:
                if (state == State.start)  setState(State.move);
                else if (state == State.move)  setState(State.superBuild);
                else if (state == State.superBuild)  setState(State.secondTimeState);
                else if (state == State.secondTimeState)  setState(State.endTurn);
                break;

            case Prometheus:
                if (state == State.start)  setState(State.build);
                else if (state == State.superMove)  setState(State.build);
                else if (state == State.build) {
                    if (!again) {
                        again = true;
                        setState(State.superMove);
                    } else {
                        again = false;
                        setState(State.endTurn);
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
                if (state == State.superMove) state = State.start;
                else if (state == State.secondTimeState) state = State.superMove;
                else if (state == State.build) state = State.secondTimeState;
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
                else if (state == State.superBuild) state = State.move;
                else if (state == State.secondTimeState) state = State.superBuild;
                else if (state == State.endTurn) state = State.secondTimeState;
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

    protected void setPath(Divinity divinity) {
        this.divinity = divinity;
    }


}
