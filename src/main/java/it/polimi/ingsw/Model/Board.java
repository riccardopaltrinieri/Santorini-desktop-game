package it.polimi.ingsw.Model;

/**
 * This is the board of the game
 */
public class Board {
    private int numRow=5;
    private int numColumn=5;
public Cell[][] map;

    /**
     * This is the constructor of method
     */
    public Board () {
        int row;
        int y;
        map = new Cell[5][5];
        for (row = 0; row < 5; row++) {
            for (y = 0; y < 5; y++) {
                this.map[row][y]=new Cell(row,y);
            }
        }
    }

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

    /**
     * When a player is eliminated this method brings back isEmpty=true
     * @param cell is the position of worker
     */
    public void clearCell(Cell cell){
        cell.setEmptyDefault();
    }

    /**
     * With this method we can eliminate all players
     */
    public void clearAll() {
        int x;
        int y;
        for (x = 0; x < 5; x++) {
            for (y = 0; y < 5; y++) {
                this.map[x][y].setEmptyDefault();
            }
        }
    }




    }



