package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Divinity;

public class FiniteStateMachine {

    private State state;
    private Divinity divinity;
    private boolean again;


    public void nextState() throws IllegalStateException {


        switch (this.divinity) {

            case Default:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;

            case Apollo:
                if (state == State.start) state = State.superMove;
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;
            case Artemis:
                if (state == State.start) {
                    state = State.superMove;
                    again=false;
                }
                else if (state == State.superMove) {
                    if (!again) {
                        again = true;
                    } else {
                        again = false;
                        state = State.build;
                    }
                }
                else if (state == State.build) state = State.endTurn;
                break;
            case Athena:
                if (state == State.start) state = State.superMove;
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;
            case Atlas:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.superBuild;
                else if (state == State.superBuild) state = State.endTurn;
                break;
            case Demeter:
                if (state == State.start) {
                    state = State.move;
                    again=false;
                }
                else if (state == State.move) state = State.superBuild;
                else if (state == State.superBuild) {
                    if (!again) {
                        again = true;
                        state = State.superBuild;
                    } else {
                        again = false;
                        state = State.endTurn;
                    }
                }
                break;
            case Hephaestus:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.superBuild;
                else if (state == State.superBuild) state = State.endTurn;
                break;
            case Minotaur:
                if (state == State.start) state = State.superMove;
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;
            case Pan:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;
            case Prometheus:
                if (state == State.start){
                    again=false;
                    state = State.build;
                }
                else if (state == State.superMove) state = State.build;
                else if (state == State.build) {
                    if (!again) {
                        again = true;
                        state = State.superMove;
                    } else {
                        again = false;
                        state = State.endTurn;
                    }
                }
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + this.divinity);
        }
    }
//  ************** GETTER AND SETTER **************************

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPath(Divinity divinity) {
        this.divinity = divinity;
    }


}
