package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.View.Graphics.ChooseFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ChooseFrameListener implements ItemListener, ActionListener {
    ChooseFrame frame;
    public ChooseFrameListener(ChooseFrame frame){
        this.frame=frame;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==ItemEvent.SELECTED){
            String path="images/godCards/"+frame.getDivinityList().getSelectedItem()+".png";
            frame.setGodIcon(new ImageIcon(path));
            frame.getGodLabel().setIcon(frame.getGodIcon());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((JButton)e.getSource()==frame.getSelectButton()){
            frame.setChosenDivinity(frame.getDivinityList().getSelectedItem().toString());
            synchronized (frame) {
                frame.notifyAll();
            }
            frame.dispose();

        }
    }

}
