package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoosePanel extends JPanel{

    private JPanel godPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
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

    public ChoosePanel(){
        for (int i=0;i<10;i++){
            divinityString.add(Divinity.values()[i].toString());
        }
    }

    public void init(){
        chosenDivinity="";
        ChoosePanelListener choosePanelListener = new ChoosePanelListener(this);
        setLayout(new BorderLayout());

        divinityList = new JComboBox(divinityString.toArray());
        divinityList.addItemListener(choosePanelListener);

        comboPanel.add(divinityList);
        comboPanel.add(selectButton);
        //godInfoText.setText(Divinity.getDescrption(divinityList.getSelectedItem().toString().toLowerCase()));
        godInfoText.setEditable(false);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(comboPanel,BorderLayout.NORTH);
        centerPanel.add(godInfoText,BorderLayout.CENTER);
        selectButton.addActionListener(choosePanelListener);

        add(centerPanel,BorderLayout.CENTER);

        String iconPath = "images/godCards/"+divinityList.getSelectedItem()+".png";
        godIcon= new ImageIcon(iconPath);
        godLabel= new JLabel(godIcon);
        godPanel.setLayout(new BorderLayout());
        godPanel.add(godLabel, BorderLayout.CENTER);
        add(godPanel,BorderLayout.WEST);

        infoText.setEditable(false);
        infoText.setAlignmentX(CENTER_ALIGNMENT);
        add(infoText,BorderLayout.NORTH);

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
