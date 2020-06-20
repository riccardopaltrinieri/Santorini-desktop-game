package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private final int cellSize=90;

    private Icon godCard = new ImageIcon("images/godCards/None.png");
    private Icon[] cellBoardIcon = new ImageIcon[49];

    private JLabel godLabel = new JLabel(godCard);

    private JPanel bottomPanel = new JPanel();
    private JPanel godInfoPanel = new JPanel();
    private JPanel mapPanel = new JPanel();
    private JPanel yesOrNoPanel = new JPanel();

    private BoardButton[] activeBoardButtons = new BoardButton[25];
    private BoardButton[] boardButtons = new BoardButton[49];

    private JTextArea textArea = new JTextArea();
    private JTextArea playerInfoTextArea = new JTextArea();

    private JButton endTurnButton = new JButton("End Turn");
    private JButton yesButton = new JButton("Yes");
    private JButton noButton = new JButton("No");

    private String yesOrNoString;

    private BoardButton chosenButton;

    private void registerPlayer(){
        
    }

    public void init(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        setLayout(layout);

        lim.gridy=1;
        lim.gridwidth=3;
        lim.gridheight=5;
        layout.setConstraints(godLabel,lim);
        add(godLabel);

        /*godInfoPanel.add(playerInfoTextArea);
        playerInfoTextArea.setEditable(false);
        lim.gridy=6;
        lim.gridheight=1;
        layout.setConstraints(godInfoPanel,lim);
        add(godInfoPanel);
        */


        lim.gridy=8;
        lim.gridx=1;
        lim.gridwidth=3;
        lim.gridheight=1;
        bottomPanel.add(textArea);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        layout.setConstraints(bottomPanel,lim);
        add(bottomPanel);

        mapPanel.setLayout(new GridLayout(7,7));
        int j=0;
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
            boardButtons[i] = new BoardButton("",cellBoardIcon[i]);
            boardButtons[i].setRow(row-1);
            boardButtons[i].setColumn(column-1);
            boardButtons[i].setDisabledIcon(cellBoardIcon[i]);
            boardButtons[i].setPreferredSize(new Dimension(cellSize,cellSize));
            boardButtons[i].setMaximumSize(new Dimension(cellSize,cellSize));
            if ((row==1)||(row==7)||(column==1)||(column==7)){
                boardButtons[i].setEnabled(false);
            }
            else{
                activeBoardButtons[j]= boardButtons[i];
                activeBoardButtons[j].setEnabled(false);
                j++;
            }
            mapPanel.add(boardButtons[i]);
        }
        lim.gridy=1;
        lim.gridx=3;
        lim.gridwidth=7;
        lim.gridheight=7;
        layout.setConstraints(mapPanel,lim);
        add(mapPanel);

        lim.gridx=4;
        lim.gridy=8;
        lim.gridwidth=3;
        lim.gridheight=1;
        layout.setConstraints(yesOrNoPanel,lim);
        yesOrNoPanel.setPreferredSize(new Dimension(200,50));
        add(yesOrNoPanel);

        lim.gridx=7;
        lim.gridwidth=1;
        endTurnButton.setEnabled(false);
        layout.setConstraints(endTurnButton,lim);
        add(endTurnButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Santorini Game");
        pack();
        setVisible(true);
    }

    public void updateTextArea(String text){
        textArea.setText(text);
    }

    public void updatePlayerInfoTextArea(String text){
        String oldText=playerInfoTextArea.getText();
        if (oldText!=""){
            oldText+="\n";
        }
        playerInfoTextArea.setText(oldText + " " + text);
    }

    public void updateGodCard(String godName){
        String path="images/godCards/"+godName+".png";
        godCard=new ImageIcon(path);
        godLabel.setIcon(godCard);
    }

    public BoardButton[] getActiveBoardButtons() {
        return activeBoardButtons;
    }

    public void setChosenButton(BoardButton chosenButton) {
        this.chosenButton = chosenButton;
    }

    public synchronized int[] getChosenButtonCoordinate() {
        synchronized (this) {
            try {
                wait();
                int[] chosenButtonCoordinate = new int[2];
                chosenButtonCoordinate[0]=chosenButton.getRow();
                chosenButtonCoordinate[1]=chosenButton.getColumn();
                return chosenButtonCoordinate;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return getChosenButtonCoordinate();
            }
        }
    }

    public synchronized String getYesOrNoResponse(){
        synchronized (this){
            try{
                wait();
                return yesOrNoString;
            }
            catch (InterruptedException e){
                e.printStackTrace();
                return yesOrNoString;
            }
        }
    }

    public void addYesOrNo(){

        yesOrNoPanel.add(yesButton);
        yesOrNoPanel.add(noButton);
    }

    public void removeYesOrNo(){
        yesOrNoPanel.remove(yesButton);
        yesOrNoPanel.remove(noButton);
    }

    public JButton getYesButton() {
        return yesButton;
    }

    public JButton getNoButton() {
        return noButton;
    }

    public String getYesOrNoString() {
        return yesOrNoString;
    }

    public void setYesOrNoString(String yesOrNoString) {
        this.yesOrNoString = yesOrNoString;
    }

    public void enableEndTurn(){
        endTurnButton.setEnabled(true);
    }

    public void disableEndTurn(){
        endTurnButton.setEnabled(false);
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }
}
