package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import java.awt.*;

/**
 * it's the panel that let the second or the third player choose between the divinity taken from the first player
 */
public class ChooseBetweenPanel extends JPanel {
    private final JPanel upperPanel = new JPanel();
    private final JPanel godPanel = new JPanel();
    private final JPanel leftGodPanel = new JPanel();
    private final JPanel centralGodPanel = new JPanel();
    private final JPanel rightGodPanel = new JPanel();

    private final JTextArea chooseText = new JTextArea("Choose Your Divinity");

    private String path;
    private String chosenDivinity;

    ChooseBetweenListener listener = new ChooseBetweenListener(this);

    /**
     * this method initialize all the graphic for the panel in case of the choice has to be made between two GodPowers
     * @param firstGod it's the name of the first GodPower
     * @param secondGod it's the second of the GodPower
     */
    public void init(String firstGod, String secondGod) {
        setAlignmentX(CENTER_ALIGNMENT);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900,680));
        chooseText.setFont(new Font("Times New Roman",Font.PLAIN,15));
        upperPanel.add(chooseText);
        upperPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(upperPanel, BorderLayout.NORTH);

        path = "images/godCards/" + firstGod + ".png";
        Icon leftGodIcon = new ImageIcon(path);
        JLabel leftGodLabel = new JLabel(leftGodIcon);
        JButton leftGodButton = new JButton(firstGod);
        leftGodButton.addActionListener(listener);
        leftGodPanel.setLayout(new BorderLayout());
        leftGodPanel.add(leftGodLabel, BorderLayout.CENTER);
        leftGodPanel.add(leftGodButton, BorderLayout.SOUTH);

        path = "images/godCards/" + secondGod + ".png";
        Icon rightGodIcon = new ImageIcon(path);
        JLabel rightGodLabel = new JLabel(rightGodIcon);
        JButton rightGodButton = new JButton(secondGod);
        rightGodButton.addActionListener(listener);
        rightGodPanel.setLayout(new BorderLayout());
        rightGodPanel.add(rightGodLabel, BorderLayout.CENTER);
        rightGodPanel.add(rightGodButton, BorderLayout.SOUTH);

        godPanel.setLayout(new BorderLayout());
        godPanel.add(rightGodPanel, BorderLayout.EAST);
        godPanel.add(leftGodPanel, BorderLayout.WEST);

        add(godPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * It's the method that initialize the Panel for the choice between three GodPowers, at first calls the normal
     * method for the first and second god and adds the third one
     * @param firstGod it's the name of the first GodPower
     * @param secondGod it's the name of the second GodPower
     * @param thirdGod it's the name of the third GodPower
     */
    public void init(String firstGod, String secondGod, String thirdGod) {
        init(firstGod, secondGod);
        path = "images/godCards/" + thirdGod + ".png";
        Icon centralGodIcon = new ImageIcon(path);
        JLabel centralGodLabel = new JLabel(centralGodIcon);
        JButton centralGodButton = new JButton(thirdGod);
        centralGodButton.addActionListener(listener);
        centralGodPanel.setLayout(new BorderLayout());
        centralGodPanel.add(centralGodLabel, BorderLayout.CENTER);
        centralGodPanel.add(centralGodButton, BorderLayout.SOUTH);

        godPanel.add(centralGodPanel, BorderLayout.CENTER);
    }

    /**
     * it's used to take the choice of the player and it's syncronized on the frame to wait for the player
     * while he makes his choice
     * @return the GodPower chosen by the player;
     */
    public synchronized String getChosenDivinity() {
        synchronized (this) {
            try {
                wait();
                return chosenDivinity;
            } catch (InterruptedException e) {
                return "error";
            }
        }
    }

    public void setChosenDivinity(String chosenDivinity) {
        this.chosenDivinity = chosenDivinity;
    }
}

