package it.polimi.ingsw.Model;

public class Board {
    private int numRow;
    private int numColumn;
public Cell[][] map;

    public Board () {
        int i;
        int j;
        this.numRow=5;
        this.numColumn=5;
      /*  Cell[][] map=new Cell[5][5];
       for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.map[i][j].setEmptyDefault();
                this.map[i][j].setNumRow(i);
                this.map[i][j].setNumColumn(j);
       } */

}


    public int getNumRow() {
        return numRow;
    }

    public int getNumColumn() {
        return numColumn;
    }




    }



