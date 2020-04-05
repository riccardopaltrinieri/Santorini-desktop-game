package it.polimi.ingsw.Model;

public class Cell {
    private int numRow;
    private int numColumn;
    private int level;
    private boolean isEmpty;

    /**
     * it's the constructor of the class
     * @param row  is the row where you want to place the cell
     * @param column is the column where you want to place the cell
     * @throws IllegalArgumentException if you are trying to create a cell outside of the 5x5 tab
     */
    public Cell (int row, int column) throws IllegalArgumentException{
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

    public boolean getIsEmpty(){
        return this.isEmpty;
    }

    /**
     *
     * @param destination is the cell that you want to know if it's reachable
     * @return true if the cell is reachable or false if it's not
     */
    public boolean canMoveTo(Cell destination) {
        return  (destination.getNumRow() >= 0) && (destination.getNumRow() <= 4) &&
                (destination.getNumColumn() >= 0) && (destination.getNumColumn() <= 4) &&
                (destination.getNumRow() >= this.numRow - 1) &&
                (destination.getNumRow() <= this.numRow + 1) &&
                (destination.getNumColumn() >= this.numColumn - 1) &&
                (destination.getNumColumn() <= this.numColumn + 1) &&
                (destination.getLevel() >= this.level - 1) &&
                (destination.getLevel() <= this.level + 1) &&
                (destination.getIsEmpty());
    }

    /**
     *
     * @param destination is the cell where you want to know if you can build in
     * @return true if you can build in the destination cell or false if you can't
     */
    public boolean canBuildIn(Cell destination){
        return ((destination.getNumRow() >= 0) && (destination.getNumRow() <= 4) &&
                (destination.getNumColumn() >= 0) && (destination.getNumColumn() <= 4) &&
                destination.getNumRow() >= this.numRow - 1) &&
                (destination.getNumRow() <= this.numRow + 1) &&
                (destination.getNumColumn() >= this.numColumn - 1) &&
                (destination.getNumColumn() <= this.numColumn + 1) &&
                (destination.getIsEmpty()) && (destination.getLevel() <= 4);
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
        if(0 <= level && level <= 5) this.level = level;
    }
    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

}
