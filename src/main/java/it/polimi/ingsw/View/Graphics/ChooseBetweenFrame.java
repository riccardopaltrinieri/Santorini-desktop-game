package it.polimi.ingsw.View.Graphics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ChooseBetweenFrame extends JFrame {
    private JPanel upperPanel = new JPanel();
    private JPanel godPanel = new JPanel();
    private JPanel leftGodPanel = new JPanel();
    private JPanel centralGodPanel = new JPanel();
    private JPanel rightGodPanel = new JPanel();

    private Icon leftGodIcon;
    private Icon centralGodIcon;
    private Icon rightGodIcon;

    private JButton leftGodButton;
    private JButton centralGodButton;
    private JButton rightGodButton;

    private JLabel leftGodLabel;
    private JLabel centralGodLabel;
    private JLabel rightGodLabel;

    private JTextArea chooseText = new JTextArea("Choose Your Divinity");

    private String path;
    private String chosenDivinity;

    ChooseBetweenListener listener = new ChooseBetweenListener(this);

    public void init(String firstGod, String secondGod) {
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());

        upperPanel.add(chooseText);
        upperPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(upperPanel, BorderLayout.NORTH);

        path = "images/godCards/" + firstGod + ".png";
        leftGodIcon = new ImageIcon(path);
        leftGodLabel = new JLabel(leftGodIcon);
        leftGodButton = new JButton(firstGod);
        leftGodButton.addActionListener(listener);
        leftGodPanel.setLayout(new BorderLayout());
        leftGodPanel.add(leftGodLabel, BorderLayout.CENTER);
        leftGodPanel.add(leftGodButton, BorderLayout.SOUTH);

        path = "images/godCards/" + secondGod + ".png";
        rightGodIcon = new ImageIcon(path);
        rightGodLabel = new JLabel(rightGodIcon);
        rightGodButton = new JButton(secondGod);
        rightGodButton.addActionListener(listener);
        rightGodPanel.setLayout(new BorderLayout());
        rightGodPanel.add(rightGodLabel, BorderLayout.CENTER);
        rightGodPanel.add(rightGodButton, BorderLayout.SOUTH);

        godPanel.setLayout(new BorderLayout());
        godPanel.add(rightGodPanel, BorderLayout.EAST);
        godPanel.add(leftGodPanel, BorderLayout.WEST);

        add(godPanel, BorderLayout.CENTER);
        setTitle("Choose Divinity");
        pack();
        setVisible(true);
    }

    public void init(String firstGod, String secondGod, String thirdGod) {
        init(firstGod, secondGod);
        path = "images/godCards/" + thirdGod + ".png";
        centralGodIcon = new ImageIcon(path);
        centralGodLabel = new JLabel(centralGodIcon);
        centralGodButton = new JButton(thirdGod);
        centralGodButton.addActionListener(listener);
        centralGodPanel.setLayout(new BorderLayout());
        centralGodPanel.add(centralGodLabel, BorderLayout.CENTER);
        centralGodPanel.add(centralGodButton, BorderLayout.SOUTH);

        godPanel.add(centralGodPanel, BorderLayout.CENTER);
        pack();
    }

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

