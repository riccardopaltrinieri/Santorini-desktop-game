package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.View.FSMView;
import it.polimi.ingsw.View.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButtonListener implements ActionListener {

    FSMView fsm;
    Color color;
    MainFrame frame;

    public BoardButtonListener(FSMView fsm, Color color,MainFrame frame){
        this.color = color;
        this.fsm=fsm;
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (fsm.getState()== State.placeworker){
            ((BoardButton)e.getSource()).setWorkerColor(color);
            ((BoardButton)e.getSource()).setHaveWorker(true);
            ((BoardButton)e.getSource()).repaint();
            frame.setChosenButton((BoardButton)e.getSource());
            synchronized (frame) {
                frame.notifyAll();
            }
        }
    }
}
