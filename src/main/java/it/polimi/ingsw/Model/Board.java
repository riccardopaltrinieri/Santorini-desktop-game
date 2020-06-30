package it.polimi.ingsw.Model;

/**
 * This is the board of the game
 */
public class Board {
    private static final int numRow=5;
    private static final int numColumn=5;
    private Cell[][] map;

    /**
     * This is the constructor of method
     */
    public Board () {
        int row;
        int column;
        map = new Cell[5][5];
        for (row = 0; row < 5; row++)
            for (column = 0; column < 5; column++)
                this.map[row][column] = new Cell(row, column);
    }

    public Board(Cell[][] map) {
        this.map = map;
    }

    /**
     * When a player is eliminated this method brings back isEmpty=true
     * @param cell is the position of worker
     */
    public void clearCell(Cell cell){
        cell.setEmpty(true);
    }

    /**
     * With this method we can eliminate all players
     */
    public void clearAll() {
        int row;
        int column;
        for (row = 0; row < 5; row++)
            for (column = 0; column < 5; column++)
                this.map[row][column].setEmpty(true);
    }

    public Cell[][] getMapCopy() {
        Cell[][] mapCopy = new Cell[5][5];

        int row;
        int column;
        for (row = 0; row < 5; row++) {
            for (column = 0; column < 5; column++) {
                mapCopy[row][column] = new Cell(row, column);
                mapCopy[row][column].setLevel(this.map[row][column].getLevel());
                mapCopy[row][column].setEmpty(true);
            }
        }
        return mapCopy;
    }

// *************** GETTER AND SETTER *********************************

    /**
     * @return the number of Row
     */
    public int getNumRow() {
        return numRow;
    }

    /**
     * @return  the number of Column
     */
    public int getNumColumn() {
        return numColumn;
    }

    public Cell[][] getMap() {
        return map;
    }
    public Cell getCell(int row, int column) {
        return map[row][column];
    }

    }



