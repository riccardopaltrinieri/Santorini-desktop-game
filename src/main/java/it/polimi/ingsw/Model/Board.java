package it.polimi.ingsw.Model;

public class Board {
    private int numRow=5;
    private int numColumn=5;
private Cell[][] map;

    public Board () {
        int i;
        int j;

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.map[i][j].setEmptyDefault();
                this.map[i][j].setNumRow(i);
                this.map[i][j].setNumColumn(j);
            }
        }
       // System.out.println(this.map[1][1].getNumRow());
    }


    public int getNumRow() {
        return numRow;
    }

    public int getNumColumn() {
        return numColumn;
    }




    }



