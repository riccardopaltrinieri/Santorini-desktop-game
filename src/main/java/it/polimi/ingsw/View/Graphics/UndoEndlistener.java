package it.polimi.ingsw.View.Graphics;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.View.FSMView;
import it.polimi.ingsw.utils.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoEndlistener implements ActionListener {
    private MainFrame frame;
    public UndoEndlistener(MainFrame frame){
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if((e.getSource())==frame.getEndTurnButton()){
            synchronized (frame) {
                frame.notifyAll();
            }
        }
        if((e.getSource())==frame.getUndoButton()){
            synchronized (frame) {
                frame.notifyAll();
                frame.setUndo(true);
            }
        }
    }
}
