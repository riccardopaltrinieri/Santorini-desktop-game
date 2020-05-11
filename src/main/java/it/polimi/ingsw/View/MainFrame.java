package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.GodPower;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainFrame extends JFrame{

    private JPanel northerPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JPanel southerPanel = new JPanel();

    private JPanel godPanel = new JPanel();
    private JPanel mapPanel = new JPanel();

    private JPanel godCardPanel = new JPanel();
    private JPanel infoGodPanel = new JPanel();

    private JButton[] cellButtons = new JButton[49];

    private final int godCardHeight =282;//282
    private final int godCardWidth = 168;//168
    private Icon apolloCard = new ImageIcon("images/godCards/Apollo.png");

    private final int cellBoardHeight=80;
    private final int cellBoardWidth=80;
    private Icon[] cellBoardIcon = new ImageIcon[49];

    public void initGUI(){
        //godcardPanel
        JLabel godCardLabel = new JLabel(apolloCard);
        godCardPanel.add(godCardLabel);

        //infogodPanel
        JTextField godCardText = new JTextField("il tuo potere del dio ti permette di essere un figo incredibile ma non quanto fillo, lui è insuperabile. anto e richi puzzano");
        godCardText.setEditable(false);
        infoGodPanel.add(godCardText);

        //godPanel
        godPanel.setLayout(new BorderLayout());
        godPanel.setPreferredSize(new Dimension(godCardWidth,godCardHeight));
        godPanel.add(godCardLabel,BorderLayout.NORTH);
        godPanel.add(infoGodPanel,BorderLayout.SOUTH);

        //mapPanel
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
            cellButtons[i].setPreferredSize(new Dimension(cellBoardWidth,cellBoardHeight));
            mapPanel.add(cellButtons[i]);
        }

        //centerPanel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(godPanel, BorderLayout.WEST);
        centerPanel.add(mapPanel,BorderLayout.EAST);

        //northerPanel
        JButton startButton = new JButton("Start New Game");
        JButton endButton = new JButton("end turn");
        northerPanel.add(startButton);
        northerPanel.add(endButton);

        //southerPanel
        JTextField infotext = new JTextField("seleziona sulla mappa dove desidero muovere il tuo worker");
        infotext.setEditable(false);
        southerPanel.add(infotext);

        //mainFrame
        setLayout(new BorderLayout());
        add(northerPanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(southerPanel,BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Santorini Game");
        pack();
        setVisible(true);

    }
}
