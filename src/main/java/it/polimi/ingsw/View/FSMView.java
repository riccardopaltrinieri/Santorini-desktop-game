package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.State;
import it.polimi.ingsw.Model.Divinity;

public class FSMView {
    private State state;
    private Divinity divinity;
    private boolean again;

    /**
     * constructor of the class, it set the initial state to "start"
     */
    public FSMView(){
        this.state = State.start;
        this.again = false;
        this.divinity = Divinity.Default;
    }

    /**
     * Increments the state of the FSM following the player's divinity customized path
     * @throws IllegalStateException when the divinity is not recognized
     */
    protected void nextState() throws IllegalStateException {


        switch (this.divinity) {

            case Default:
            case Apollo:
            case Athena:
            case Minotaur:
            case Pan:
            case Atlas:
            case Hephaestus:
                if (state == State.start) state = State.move;
                else if (state == State.move) state = State.build;
                else if (state == State.build) state = State.endTurn;
                break;

            case Artemis:
                if (state == State.start) {
                    state = State.move;
                    again=false;
                }
                else if (state == State.move) {
                    if (!again) {
                        again = true;
                    } else {
                        again = false;
                        state = State.build;
                    }
                }
                else if (state == State.build) state = State.endTurn;
                break;

            case Demeter:
                if (state == State.start) {
                    state = State.move;
                    again=false;
                }
                else if (state == State.move) state = State.build;
                else if (state == State.build) {
                    if (!again) {
                        again = true;
                        state = State.build;
                    } else {
                        again = false;
                        state = State.endTurn;
                    }
                }
                break;

            case Prometheus:
                if (state == State.start){
                    again=false;
                    state = State.build;
                }
                else if (state == State.move) state = State.build;
                else if (state == State.build) {
                    if (!again) {
                        again = true;
                        state = State.move;
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

    protected void setState(State state) {
        this.state = state;
    }

    protected void setPath(Divinity divinity) {
        this.divinity = divinity;
    }


}
