package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.View.State;
import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame{

    private Icon background = new ImageIcon("images/LoadingBackground.jpeg");
    private Icon godCard = new ImageIcon("images/godCards/None.png");
    private final Icon[] cellBoardIcon = new ImageIcon[49];

    private final JLabel godLabel = new JLabel(godCard);
    private JLabel backgroundLabel = new JLabel();

    private JPanel mainPanel = new JPanel();
    private final JPanel mapPanel = new JPanel();
    private final JPanel yesOrNoPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel endUndoPanel = new JPanel();
    private final JPanel textPanel = new JPanel();

    private final BoardButton[] activeBoardButtons = new BoardButton[25];
    private final BoardButton[] boardButtons = new BoardButton[49];

    private final JTextArea textArea = new JTextArea();
    private final JTextArea playerInfoTextArea = new JTextArea();

    private final JButton endTurnButton = new JButton("End Turn");
    private final JButton powerButton = new JButton("Use Power");
    private final JButton defaultButton = new JButton("Normal turn");
    private final JButton undoButton = new JButton("Undo");

    private boolean undo=false;

    private String yesOrNoString;

    private BoardButton chosenButton;

    public void startingInit(){
        backgroundLabel.setIcon(background);
        add(backgroundLabel);
        pack();
        setVisible(true);
    }

    public void init(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lim = new GridBagConstraints();
        mainPanel.setLayout(layout);

        int cellSize = 90;
        lim.gridy=1;
        lim.gridwidth=3;
        lim.gridheight=5;
        layout.setConstraints(godLabel,lim);
        mainPanel.add(godLabel);

        lim.gridy=6;
        lim.gridx=1;
        lim.gridheight=2;
        lim.gridwidth=3;
        layout.setConstraints(playerInfoTextArea,lim);
        mainPanel.add(playerInfoTextArea);
        playerInfoTextArea.setEditable(false);

        lim.gridy=8;
        lim.gridx=1;
        lim.gridwidth=14;
        lim.gridheight=2;
        lim.fill=GridBagConstraints.HORIZONTAL;
        bottomPanel.setLayout(new BorderLayout(10,100));
        textPanel.add(textArea);
        bottomPanel.add(textPanel, BorderLayout.WEST);

        bottomPanel.add(yesOrNoPanel,BorderLayout.CENTER);

        endTurnButton.setEnabled(false);
        endUndoPanel.add(endTurnButton);
        undoButton.setEnabled(false);
        endUndoPanel.add(undoButton);
        bottomPanel.add(endUndoPanel,BorderLayout.EAST);
        layout.setConstraints(bottomPanel,lim);
        mainPanel.add(bottomPanel);

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
            boardButtons[i].setPreferredSize(new Dimension(cellSize, cellSize));
            boardButtons[i].setMaximumSize(new Dimension(cellSize, cellSize));
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
        mainPanel.add(mapPanel);

        /*lim.gridx=4;
        lim.gridy=8;
        lim.gridwidth=3;
        lim.gridheight=1;
        layout.setConstraints(yesOrNoPanel,lim);
        yesOrNoPanel.setPreferredSize(new Dimension(200,50));
        yesOrNoPanel.setLayout(new BorderLayout());
        mainPanel.add(yesOrNoPanel);*/

        /*lim.gridx=8;
        lim.gridwidth=1;
        lim.weightx=100;
        endTurnButton.setEnabled(false);
        layout.setConstraints(endTurnButton,lim);
        mainPanel.add(endTurnButton);
        lim.gridx=9;
        layout.setConstraints(undoButton,lim);
        undoButton.setEnabled(false);
        mainPanel.add(undoButton);*/

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Santorini Game");
        add(mainPanel);
        pack();
        setVisible(true);
    }

    public void updateActiveBoardButtons(State state, Divinity divinity, int worker) {
        switch (state){
            case move -> {
                for (BoardButton button : activeBoardButtons)
                    button.setSelectableCell(getWorkerButton(worker).canMoveTo(button, divinity));
            }
            case build -> {
                for (BoardButton button : activeBoardButtons)
                    button.setSelectableCell(getWorkerButton(worker).canBuildIn(button));
            }
        }
    }

    public void updateTextArea(String text){
        textArea.setText(text);
        textArea.setEditable(false);
    }

    public void updatePlayerInfoTextArea(String text){
        String oldText=playerInfoTextArea.getText();
        if (!oldText.isBlank()){
            oldText+="\n";
        }
        playerInfoTextArea.setText(oldText + " " + text);
        playerInfoTextArea.setEditable(false);
    }

    public void updateGodCard(String godName){
        String path="images/godCards/"+godName+".png";
        godCard=new ImageIcon(path);
        godLabel.setIcon(godCard);
    }

    public BoardButton[] getActiveBoardButtons() {
        return activeBoardButtons;
    }

    public BoardButton getActiveButton(int index) {
        return activeBoardButtons[index];
    }

    public BoardButton getWorkerButton(int worker) {
        for (BoardButton button : activeBoardButtons)
            if (button.getWorkerNum() == worker)
                return button;

        return null;
    }

    public void setChosenButton(BoardButton chosenButton) {
        this.chosenButton = chosenButton;
    }

    public synchronized int[] getChosenButtonCoordinate() {
        synchronized (this) {
            try {
                wait();
                if(undo) return new int[]{-1, -1};
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

        yesOrNoPanel.add(powerButton);
        yesOrNoPanel.add(defaultButton);
    }

    public void removeYesOrNo(){
        yesOrNoPanel.remove(powerButton);
        yesOrNoPanel.remove(defaultButton);
    }

    public JButton getPowerButton() {
        return powerButton;
    }

    public JButton getDefaultButton() {
        return defaultButton;
    }

    public boolean removeStartingPanel(){
        remove(backgroundLabel);
        return true;
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

    public JButton getUndoButton() {
        return undoButton;
    }

    public void setUndo(boolean undo) {
        this.undo = undo;
    }

    public boolean getUndo() {
        return undo;
    }
}
