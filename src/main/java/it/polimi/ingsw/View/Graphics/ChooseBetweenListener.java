package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseBetweenListener implements ActionListener {

    ChooseBetweenFrame frame;

    public ChooseBetweenListener(ChooseBetweenFrame frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setChosenDivinity(((JButton)e.getSource()).getText());
        synchronized (frame) {
            frame.notifyAll();
        }
        frame.dispose();
    }
}
