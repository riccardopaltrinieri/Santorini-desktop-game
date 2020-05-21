package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Divinity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChooseFrame extends JFrame{

    private JPanel godPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel comboPanel = new JPanel();

    private JTextArea infoText = new JTextArea();

    private Icon godIcon;
    private JLabel godLabel;

    private JButton selectButton = new JButton("Select");

    private JComboBox divinityList;

    private String chosenDivinity;
    private ArrayList<String> divinityString= new ArrayList<String>();

    void init(){
        setLayout(new BorderLayout());

        for (int i=0;i<10;i++){
            divinityString.add(Divinity.values()[i].toString());
        }
        divinityList = new JComboBox(divinityString.toArray());
        ChooseFrameListener chooseFrameListener = new ChooseFrameListener(this);
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
        infoText.setText("descizione rapida del potere del dio");
        infoText.setEditable(false);
        godPanel.add(infoText,BorderLayout.SOUTH);
        add(godPanel,BorderLayout.CENTER);


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

    public String getChosenDivinity() {
        return chosenDivinity;
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
}
