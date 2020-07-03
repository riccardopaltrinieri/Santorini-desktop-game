package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.utils.Color;
import it.polimi.ingsw.View.FSMView;
import it.polimi.ingsw.View.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it's the ActionListener that listen to all the events that occur to the {@link BoardButton}
 */
public class BoardButtonListener implements ActionListener {

    FSMView fsm;
    Color color;
    final MainFrame frame;

    public BoardButtonListener(FSMView fsm, Color color,MainFrame frame){
        this.color = color;
        this.fsm=fsm;
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((fsm.getState()== State.placeworker)||(fsm.getState()==State.worker)||(fsm.getState()==State.move)||(fsm.getState()==State.build)){
            frame.setChosenButton((BoardButton)e.getSource());
            synchronized (frame) {
                frame.notifyAll();
            }
        }
        if(fsm.getState()== State.start){
            if ((e.getSource())==frame.getPowerButton()){
                frame.setYesOrNoString("usepower");
            }
            else if((e.getSource())==frame.getDefaultButton()){
                frame.setYesOrNoString("normal");
            }
            else{
                return;
            }
            synchronized (frame){
                frame.notify();
            }
        }
    }
}
