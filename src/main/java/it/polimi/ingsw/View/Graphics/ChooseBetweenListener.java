package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it's the ActionListener that listen to all the events that occur to the {@link ChooseBetweenPanel} JPanel
 */
public class ChooseBetweenListener implements ActionListener {

    ChooseBetweenPanel frame;

    public ChooseBetweenListener(ChooseBetweenPanel frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setChosenDivinity(((JButton)e.getSource()).getText());
        synchronized (frame) {
            frame.notifyAll();
        }
    }
}
