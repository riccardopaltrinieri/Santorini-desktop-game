package it.polimi.ingsw.View;

import it.polimi.ingsw.utils.Divinity;
import org.junit.Test;

import static org.junit.Assert.*;

public class FSMViewTest {

    FSMView fsm = new FSMView();

    @Test
    public void testConstructor () {
        assertEquals(fsm.getState(), State.placeworker);
    }

    @Test
    public void nextState() {

        // In the first turn the state is 2 times placeworker then endTurn
        // and then go back to start
        fsm.setState(State.placeworker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.placeworker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.endTurn);
        fsm.nextState();
        assertEquals(fsm.getState(), State.start);

        // Check of the divinities who follow the normal path
        fsm.setPath(Divinity.Default);
        fsm.setState(State.start);
        fsm.nextState();
        assertEquals(fsm.getState(), State.worker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.move);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.endTurn);

        // Check of the divinities who make three action
        fsm.setPath(Divinity.Artemis);
        fsm.setState(State.start);
        fsm.nextState();
        assertEquals(fsm.getState(), State.worker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.move);
        fsm.nextState();
        assertEquals(fsm.getState(), State.move);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.endTurn);

        fsm.setPath(Divinity.Demeter);
        fsm.setState(State.start);
        fsm.nextState();
        assertEquals(fsm.getState(), State.worker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.move);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.endTurn);

        fsm.setPath(Divinity.Prometheus);
        fsm.setState(State.start);
        fsm.nextState();
        assertEquals(fsm.getState(), State.worker);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.move);
        fsm.nextState();
        assertEquals(fsm.getState(), State.build);
        fsm.nextState();
        assertEquals(fsm.getState(), State.endTurn);

    }

    @Test
    public void prevState() {

        // If the player is placing worker the function to call is different
        fsm.setState(State.placeworker);
        fsm.nextState();
        fsm.prevStateToPlaceWorker();
        assertEquals(State.placeworker, fsm.getState());
        fsm.nextState();
        fsm.nextState();
        assertEquals(State.endTurn, fsm.getState());
        fsm.prevStateToPlaceWorker();
        assertEquals(State.placeworker, fsm.getState());
        fsm.prevStateToPlaceWorker();
        assertEquals(State.placeworker, fsm.getState());

        // Check of the divinities who follow a normal path
        fsm.setPath(Divinity.Default);
        fsm.setState(State.start);
        // from worker to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // from move to worker
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.worker, fsm.getState());
        // from build to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // from endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());

        // Check of the divinities who make three actions
        fsm.setPath(Divinity.Artemis);
        fsm.setState(State.start);
        // from worker to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // from move1 to worker
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.worker, fsm.getState());
        // from move2 to move1
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // from build to move2
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // from endturn to build
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();

        fsm.setPath(Divinity.Demeter);
        fsm.setState(State.start);
        // from worker to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // from move to worker
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.worker, fsm.getState());
        // from build1 to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // from build2 to build1
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        // from endturn to build2
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();

        fsm.setPath(Divinity.Prometheus);
        fsm.setState(State.start);
        // from worker to start
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.start, fsm.getState());
        // from build1 to worker
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.worker, fsm.getState());
        // from move to build1
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        // from build2 to move
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.move, fsm.getState());
        // from endturn to build2
        fsm.nextState();
        fsm.nextState();
        fsm.prevState();
        assertEquals(State.build, fsm.getState());
        fsm.nextState();

    }

    @Test
    public void testGetString() {

        for (State state : State.values()) {
            fsm.setState(state);
            assertNotNull(fsm.getStateStringCLI());
            assertNotNull(fsm.getStateStringGUI());
        }
    }
}