package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoosePanel extends JPanel{
    /**
     * it's the JPanel used to get the choice of the GodPowers from the player taken from a JComboBox
     * that contains all the Divinity that aren't already chosen
     */
    private final Color backGroundColor = new Color(0,0,0,5);
    private final JPanel godPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();
    private final JPanel comboPanel = new JPanel();

    private String chosenDivinity;
    private String divinityNumber="first";
    private ArrayList<String> divinityString= new ArrayList<String>();

    private final JTextArea godInfoText = new JTextArea();
    private final JTextArea infoText = new JTextArea();

    private Icon godIcon;
    private JLabel godLabel;

    private final JButton selectButton = new JButton("Select");

    private JComboBox divinityList;


    public ChoosePanel(){
        for (int i=0;i<10;i++){
            divinityString.add(Divinity.values()[i].toString());
        }
    }

    /**
     * this method initialize all the graphics element that need to be displayed for this panel
     */
    public void init(){
        setBackground(backGroundColor);
        setPreferredSize(new Dimension(900,680));
        chosenDivinity="";
        ChoosePanelListener choosePanelListener = new ChoosePanelListener(this);
        setLayout(new BorderLayout(20,20));

        divinityList = new JComboBox(divinityString.toArray());
        divinityList.addItemListener(choosePanelListener);

        comboPanel.add(divinityList);
        comboPanel.add(selectButton);
        updateGodDescription();
        godInfoText.setEditable(false);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(comboPanel,BorderLayout.NORTH);
        godInfoText.setFont(new Font("Times New Roman",Font.ITALIC,18));
        godInfoText.setBackground(backGroundColor);
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
        infoText.setBackground(backGroundColor);
        infoText.setFont(new Font("Times New Roman",Font.PLAIN,15));
        infoText.setAlignmentX(CENTER_ALIGNMENT);
        updateInfoText();
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

    public JLabel getGodLabel() {
        return godLabel;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    /**
     * it's the method used to take the choice of the player and it's synchronized on the Panel itself
     * to wait the choice of the player
     * @return the GodPower chosen by the player
     */
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

    public void removeDivinityString(String stringToRemove){
        divinityString.remove(stringToRemove);
    }

    public void updateGodDescription (){
        godInfoText.setText(Divinity.getDescrption(divinityList.getSelectedItem().toString().toLowerCase()));
    }

    public void updateInfoText(){
        infoText.setText("Decide your "+ divinityNumber +" Divinity");
    }

    public void setDivinityNumber(String string) {
        divinityNumber = string;
    }
}
