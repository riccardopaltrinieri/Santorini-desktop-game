package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodPower;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame{

    private String godinfo = " ciao sono apollo e sono fico ";

    private Icon apolloCard = new ImageIcon("images/godCards/Apollo.png");
    private Icon[] cellBoardIcon = new ImageIcon[49];

    private JLabel godLabel = new JLabel(apolloCard);

    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel godInfoPanel = new JPanel();
    private JPanel mapPanel = new JPanel();
    private JPanel endTurnPanel = new JPanel();

    private JButton[] cellButtons = new JButton[49];
    private JButton endTurnButton = new JButton("End Turn");

    private JTextArea textArea = new JTextArea();
    private JTextArea godInfoTextArea = new JTextArea();

    private String textAreaString;

    private void registerPlayer(){
        
    }

    public void initGUI(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        setLayout(layout);

        lim.gridy=1;
        lim.gridwidth=3;
        lim.gridheight=5;
        layout.setConstraints(godLabel,lim);
        add(godLabel);

        godInfoTextArea.setText(godinfo);
        godInfoTextArea.setTabSize(100);
        godInfoPanel.add(godInfoTextArea);
        lim.gridy=6;
        lim.gridheight=2;
        layout.setConstraints(godInfoPanel,lim);
        add(godInfoPanel);

        lim.gridy=8;
        lim.gridwidth=8;
        lim.gridheight=1;
        textArea.setText(textAreaString);
        bottomPanel.add(textArea);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        layout.setConstraints(bottomPanel,lim);
        add(bottomPanel);

        lim.gridx=8;
        lim.gridwidth=2;
        endTurnPanel.add(endTurnButton);
        layout.setConstraints(endTurnPanel,lim);
        add(endTurnPanel);

        mapPanel.setLayout(new GridLayout(7,7));
        int row=1;
        int column=0;
        for (int i=0;i<49;i++){
            if (column <7){
                column ++;
            }
            else{
                row++;
                column=1;
            }
            String path="images/Board/" + row + column + ".png";
            cellBoardIcon[i]= new ImageIcon(path);
            cellButtons[i] = new JButton("",cellBoardIcon[i]);
            cellButtons[i].setPreferredSize(new Dimension(85,85));
            mapPanel.add(cellButtons[i]);
        }
        lim.gridy=1;
        lim.gridx=3;
        lim.gridwidth=7;
        lim.gridheight=7;
        layout.setConstraints(mapPanel,lim);
        add(mapPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Santorini Game");
        pack();
        setVisible(true);
    }

    public void updateTextArea(String text){
        textAreaString=text;
        textArea.setText(textAreaString);
    }

    public String getTextAreaString() {
        return textAreaString;
    }

    public void setTextAreaString(String textAreaString) {
        this.textAreaString = textAreaString;
    }
}
