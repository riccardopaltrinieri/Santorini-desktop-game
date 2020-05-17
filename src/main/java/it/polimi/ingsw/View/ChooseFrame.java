package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Divinity;

import javax.swing.*;
import java.awt.*;

public class ChooseFrame extends JFrame{

    private JPanel godPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel comboPanel = new JPanel();

    private JTextArea infoText = new JTextArea();

    private Icon godIcon;
    private JLabel godLabel;

    private JButton selectButton = new JButton("Select");

    void init(){
        setLayout(new BorderLayout());

        String[] divinityString = new String[10];
        for (int i=0;i<divinityString.length;i++){
            divinityString[i]=Divinity.values()[i].toString();
        }
        JComboBox divinityList = new JComboBox(divinityString);
        comboPanel.add(divinityList);
        eastPanel.add(comboPanel);
        eastPanel.add(selectButton);
        add(eastPanel,BorderLayout.EAST);

        String iconPath = "images/godCards/"+divinityList.getSelectedItem()+".png";
        godIcon= new ImageIcon(iconPath);
        godLabel= new JLabel(godIcon);
        godPanel.setLayout(new BorderLayout());
        godPanel.add(godLabel, BorderLayout.CENTER);
        infoText.setText("descizione rapida del potere del dio");
        godPanel.add(infoText,BorderLayout.SOUTH);
        add(godPanel,BorderLayout.CENTER);


        setTitle("Choose Divinity");
        pack();
        setVisible(true);
    }
}
