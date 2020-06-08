package it.polimi.ingsw.View.Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import it.polimi.ingsw.utils.Color;

public class BoardButton extends JButton {
    private int row;
    private int column;
    private BufferedImage worker = null;
    private BufferedImage selectedFrame = null;
    private BufferedImage levelImage = null;


    private int level=0;
    private boolean haveWorker=false;
    private boolean selectableCell=false;
    private Color workerColor;
    private int workerNum=0;

    public void paint(Graphics g){
        super.paint(g);
        String path;
        if ((getLevel()==1)||(getLevel()==2)||(getLevel()==3)||(getLevel()==4)){
            path = "images/Level/Level"+getLevel()+".png";
            try {
                levelImage = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            g.drawImage(levelImage, 0, 0, null);
        }
        if (haveWorker) {
            if (workerColor == Color.White) {
                path = "images/Workers/whiteWorker.png";
            } else if (workerColor == Color.Brown) {
                path = "images/Workers/brownWorker.png";
            } else {
                path = "images/Workers/purpleWorker.png";
            }
            try {
                worker = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            g.drawImage(worker, 10, 10, null);
        }
        else if (selectableCell){
            path ="images/frameMove.png";
            try {
                selectedFrame = ImageIO.read(new File(path));
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            g.drawImage(selectedFrame,0,0,null);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getSelectableCell() {
        return selectableCell;
    }

    public void setSelectableCell(boolean selectableCell) {
        this.selectableCell = selectableCell;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(int workerNum) {
        this.workerNum = workerNum;
    }

    public void setWorkerColorNull(){
        workerColor=null;
    }

    public boolean canBuildIn(BoardButton destination){
        return ((destination.getRow() >= row - 1) &&
                (destination.getRow() <= row + 1) &&
                (destination.getColumn() >= column - 1) &&
                (destination.getColumn() <= column + 1) &&
                (!destination.getHaveWorker()) &&
                (destination.getLevel() < 4) &&
                (!this.equals(destination)));
    }

    public boolean canMoveTo(BoardButton destination) {
        // the destination must be inside the board
        return
                // one of the 8 cell adiacent the worker
                (destination.getRow() >= row - 1) &&
                (destination.getRow() <= row + 1) &&
                (destination.getColumn() >= column - 1) &&
                (destination.getColumn() <= column + 1) &&
                // should not be the same cell as worker's position
                (!destination.getHaveWorker()&& (!this.equals(destination))) &&
                        // can be maximum one level higher
                        (destination.getLevel() <= level + 1)
                 &&
                (destination.getLevel() < 4);
    }
}
