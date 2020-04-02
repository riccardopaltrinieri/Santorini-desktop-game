package it.polimi.ingsw.Model;

public class Board {
    private int numRow=5;
    private int numColumn=5;
public Cell[][] map;

    public Board () {
        int i;
        int j;
        map = new Cell[5][5];
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.map[i][j]=new Cell();
                this.map[i][j].setNumRow(i);
                this.map[i][j].setNumColumn(j);
            }

        }

    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumColumn() {
        return numColumn;
    }




    }



