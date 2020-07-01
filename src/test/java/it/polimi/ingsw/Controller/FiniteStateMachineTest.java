package it.polimi.ingsw.Controller;

import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.*;

public class FiniteStateMachineTest {

    FiniteStateMachine fsm = new FiniteStateMachine();

    @Test
    public void testNextState() {
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Default);
        fsm.nextState();
        assertEquals(State.move, fsm.getState());
        fsm.nextState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Apollo);
        fsm.nextState();
        assertEquals(State.superMove, fsm.getState());
        fsm.nextState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Artemis);
        fsm.nextState();
        assertEquals(State.superMove, fsm.getState());
        fsm.nextState();
        assertEquals(State.secondTimeState, fsm.getState());
        fsm.nextState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Atlas);
        fsm.nextState();
        assertEquals(State.move, fsm.getState());
        fsm.nextState();
        assertEquals(State.superBuild, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Demeter);
        fsm.nextState();
        assertEquals(State.move, fsm.getState());
        fsm.nextState();
        assertEquals(State.superBuild, fsm.getState());
        fsm.nextState();
        assertEquals(State.secondTimeState, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Prometheus);
        fsm.nextState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        assertEquals(State.superMove, fsm.getState());
        fsm.nextState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.nextState();
        assertEquals(State.start, fsm.getState());
    }

    @Test
    public void prevState() {
        assertEquals(State.start, fsm.getState());

        fsm.setPath(Divinity.Default);
        // From move to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From build to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // From endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        fsm.nextState();

        fsm.setPath(Divinity.Apollo);
        // From supermove to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From build to supermove
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.superMove, fsm.getState());
        // From endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        fsm.nextState();

        fsm.setPath(Divinity.Artemis);
        // From supermove to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From secondTimeState to supermove
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.superMove, fsm.getState());
        // From build to secondTimeState
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.secondTimeState, fsm.getState());
        // From endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        fsm.nextState();

        fsm.setPath(Divinity.Atlas);
        // From supermove to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From build to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // From endturn to superbuild
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.superBuild, fsm.getState());
        fsm.nextState();
        fsm.nextState();

        fsm.setPath(Divinity.Demeter);
        // From supermove to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From superBuild to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // From secondTimeState to superbuild
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.superBuild, fsm.getState());
        // From endturn to secondTimeState
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.secondTimeState, fsm.getState());
        fsm.nextState();
        fsm.nextState();

        fsm.setPath(Divinity.Prometheus);
        // From build to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // From superMove to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        // From build to supermove
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.superMove, fsm.getState());
        // From endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();
        fsm.nextState();

    }
}







































