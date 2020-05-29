package it.polimi.ingsw.View.Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import it.polimi.ingsw.Model.Color;

public class BoardButton extends JButton {
    private int row;
    private int column;
    private BufferedImage worker = null;
    private BufferedImage firstLevel = null;
    private BufferedImage secondLevel = null;
    private BufferedImage thirdLevel = null;
    private BufferedImage dome = null;

    private boolean haveWorker=false;
    private Color workerColor;

    public void paint(Graphics g){
        super.paint(g);
        if (haveWorker){
            String path;
            if (workerColor==Color.White){
                path="images/Workers/whiteWorker.png";
            }
            else if (workerColor==Color.Brown){
                path="images/Workers/brownWorker.png";
            }
            else{
                path="images/Workers/purpleWorker.png";
            }
            try{
                worker= ImageIO.read(new File(path));
            }
            catch (IOException e ){
                System.out.println(e.getMessage());
            }
            g.drawImage(worker,10,10,null);
        }
    }

    public BoardButton(String string, Icon icon){
        super(string, icon);
    }

    public boolean getHaveWorker() {
        return haveWorker;
    }

    public void setHaveWorker(boolean haveWorker) {
        this.haveWorker = haveWorker;
    }

    public Color getWorkerColor() {
        return workerColor;
    }

    public void setWorkerColor(Color workerColor) {
        this.workerColor = workerColor;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
