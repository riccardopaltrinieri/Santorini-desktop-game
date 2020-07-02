package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * A cell of the game board: it may contains a building represented with levels and a worker
 */
public class Cell implements Serializable {
    private final int numRow;
    private final int numColumn;
    private int level;
    private boolean isEmpty;

    /**
     * it's the constructor of the class
     * @param row  is the row where you want to place the cell
     * @param column is the column where you want to place the cell
     * @throws IllegalArgumentException if you are trying to create a cell outside of the 5x5 tab
     */
    public Cell (int row, int column) throws IllegalArgumentException {
        if(row<5&&column<5) {
            this.numColumn = column;
            this.numRow = row;
            this.isEmpty = true;
            this.level = 0;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public boolean equals(Cell comparativeCell) {
            return getNumColumn() == comparativeCell.getNumColumn() &&
                   getNumRow() == comparativeCell.getNumRow() &&
                   getLevel() == comparativeCell.getLevel();
    }

    //  ********** GETTER AND SETTER ******************

    public int getNumRow(){
        return this.numRow;
    }
    public int getNumColumn(){
        return this.numColumn;
    }
    public int getLevel(){
        return this.level;
    }
    public void setLevel(int level) {
        if(0 <= level && level <= 4) this.level = level;
    }
    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty(){
        return this.isEmpty;
    }

}
