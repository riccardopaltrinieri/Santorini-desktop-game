package it.polimi.ingsw.View.Graphics;

import it.polimi.ingsw.View.State;
import it.polimi.ingsw.utils.Divinity;

import javax.swing.*;
import java.awt.*;

/**
 * it's the class that handle all the main graphics element of the game like the JFrame where everything
 * it's shown, a JPanel used to display a background for the registration of the player and finally
 * the ain panel where it's shown all the real game once it's started
 */
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

    private final Font font = new Font("Times New Roman",Font.PLAIN,16);

    /**
     * initialize a "loading background" that is displayed during the player's registration or when a player
     * is waiting for the others to register
     */
    public void startingInit(){
        backgroundLabel.setIcon(background);
        add(backgroundLabel);
        pack();
        setVisible(true);
    }

    /**
     * that's the methods that initialize the main JPanel that have to be displayed once the real game starts
     * and it's used to display all the main component like the game board
     */
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
        playerInfoTextArea.setFont(font);
        mainPanel.add(playerInfoTextArea);
        playerInfoTextArea.setEditable(false);

        lim.gridy=8;
        lim.gridx=1;
        lim.gridwidth=14;
        lim.gridheight=2;
        lim.fill=GridBagConstraints.HORIZONTAL;
        bottomPanel.setLayout(new BorderLayout(10,100));
        textArea.setFont(font);
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

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Santorini Game");
        add(mainPanel);
        pack();
        setVisible(true);
    }

    /**
     * it's a method that refresh all the board button as selectable or not based on the state of the fsm
     * in order to make active the button and to repaint them if they have changed their status
     * @param state indicate if the state of the fsm it's build or move in order to know which
     *              cells has to change their status
     * @param divinity if the player have chosen to use his GodPower that param represent the GodPower
     *                 that he's using or it's set to Default if the player isn't using any GodPower
     * @param worker represent the worker selected by the user
     */
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

    public void removeStartingPanel(){
        remove(backgroundLabel);
    }

    public void setYesOrNoString(String yesOrNoString) {
        this.yesOrNoString = yesOrNoString;
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
