package it.polimi.ingsw.View.Graphics;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.View.FSMView;
import it.polimi.ingsw.utils.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it's the ActionListener that listen for the events that occur
 * to the EndButton or the UndoButton
 */
public class UndoEndlistener implements ActionListener {
    private final MainFrame frame;
    public UndoEndlistener(MainFrame frame){
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if((e.getSource())==frame.getEndTurnButton()){
            synchronized (frame) {
                frame.notifyAll();
            }
        } else {
            synchronized (frame) {
                frame.setUndo(true);
                frame.notifyAll();
            }
        }
    }
}
