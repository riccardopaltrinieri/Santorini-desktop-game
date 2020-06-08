package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChooseFrame extends JFrame{

    private JPanel godPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel comboPanel = new JPanel();

    private String chosenDivinity;
    private String divinityNumber="first";
    private ArrayList<String> divinityString= new ArrayList<String>();

    private JTextArea godInfoText = new JTextArea();
    private JTextArea infoText = new JTextArea("Decide your "+ divinityNumber +" Divinity");

    private Icon godIcon;
    private JLabel godLabel;

    private JButton selectButton = new JButton("Select");

    private JComboBox divinityList;

    public ChooseFrame(){
        for (int i=0;i<10;i++){
            divinityString.add(Divinity.values()[i].toString());
        }
    }

    public void init(){
        chosenDivinity="";
        ChooseFrameListener chooseFrameListener = new ChooseFrameListener(this);
        setLayout(new BorderLayout());

        divinityList = new JComboBox(divinityString.toArray());
        divinityList.addItemListener(chooseFrameListener);

        eastPanel.add(divinityList);
        eastPanel.add(selectButton);
        selectButton.addActionListener(chooseFrameListener);

        add(eastPanel,BorderLayout.EAST);

        String iconPath = "images/godCards/"+divinityList.getSelectedItem()+".png";
        godIcon= new ImageIcon(iconPath);
        godLabel= new JLabel(godIcon);
        godPanel.setLayout(new BorderLayout());
        godPanel.add(godLabel, BorderLayout.CENTER);
        godInfoText.setText("descizione rapida del potere del dio");
        godInfoText.setEditable(false);
        godPanel.add(godInfoText,BorderLayout.SOUTH);
        add(godPanel,BorderLayout.CENTER);

        infoText.setEditable(false);
        infoText.setAlignmentX(CENTER_ALIGNMENT);
        add(infoText,BorderLayout.NORTH);

        setAlwaysOnTop(true);
        setTitle("Choose Divinity");
        pack();
        setVisible(true);
    }

    public Icon getGodIcon() {
        return godIcon;
    }

    public void setGodIcon(Icon godIcon) {
        this.godIcon = godIcon;
    }

    public JComboBox getDivinityList() {
        return divinityList;
    }

    public void setDivinityList(JComboBox divinityList) {
        this.divinityList = divinityList;
    }

    public JLabel getGodLabel() {
        return godLabel;
    }

    public void setGodLabel(JLabel godLabel) {
        this.godLabel = godLabel;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(JButton selectButton) {
        this.selectButton = selectButton;
    }

    public synchronized String getChosenDivinity() {
        synchronized (this) {
            try {
                wait();
                if (divinityString.contains(chosenDivinity)) {
                    return chosenDivinity;
                } else return "Error";
            } catch (InterruptedException e) {
                return "error";
            }
        }
    }

    public void setChosenDivinity(String chosenDivinity) {
        this.chosenDivinity = chosenDivinity;
    }

    public ArrayList getDivinityString() {
        return divinityString;
    }

    public void setDivinityString(ArrayList<String> divinityString) {
        this.divinityString = divinityString;
    }

    public void removeDivinityString(String stringToRemove){
        divinityString.remove(stringToRemove);
    }

    public String getDivinityNumber() {
        return divinityNumber;
    }

    public void setDivinityNumber(String divinityNumber) {
        this.divinityNumber = divinityNumber;
    }
}
