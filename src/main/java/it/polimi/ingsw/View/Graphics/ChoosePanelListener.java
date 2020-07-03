package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * it's the ActionListener that listen to all the events that occur to the {@link ChoosePanel} JPanel
 */
public class ChoosePanelListener implements ItemListener, ActionListener {
    ChoosePanel frame;
    public ChoosePanelListener(ChoosePanel frame){
        this.frame=frame;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==ItemEvent.SELECTED){
            String path="images/godCards/"+frame.getDivinityList().getSelectedItem()+".png";
            frame.setGodIcon(new ImageIcon(path));
            frame.getGodLabel().setIcon(frame.getGodIcon());
            frame.updateGodDescription();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==frame.getSelectButton()){
            frame.setChosenDivinity(frame.getDivinityList().getSelectedItem().toString());
            synchronized (frame) {
                frame.notifyAll();
            }
        }
    }

}
