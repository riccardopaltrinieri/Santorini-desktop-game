package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;

import java.io.Serializable;

public class LiteBoard implements Serializable {
    private static final long serialVersionUID = 36347531L;
    private int[][] lv; // [riga][colonna] = livello
    /* [worker] [0] = riga
     * [worker] [1] = colonna
     * [worker] [2] = COLORE (USATE PAINT )*/
    private int[][] posWorker;
    private String message;
    private transient int numWorker = 2;
    private transient int RCP = 3; // sono le tre colonne di posworker
    private int numAllWorker;

    public LiteBoard(String input){
        message = input;
        lv = new int[0][0];
        posWorker = new int[0][0];
        numAllWorker = 0;
    }
    public LiteBoard(String message, Board board, Game game) {
        lv = new int[board.getNumRow()][board.getNumColumn()];
        // TODO creare costruttore che riempia la liteboard a partire dalla board e dai player
        // TODO Aggiungere i player nei parametri
        for (Cell[] c: board.getMap()) {
            for (Cell c1:c) {
                int level = c1.getLevel();
                int row = c1.getNumRow();
                int column = c1.getNumColumn();
                lv[row][column] = level;
            }
        }
        numAllWorker = game.getPlayers().size()*numWorker;
        int numAllWorkerCopy = numAllWorker;
        posWorker = new int[numAllWorker][RCP];
        for (Player p: game.getPlayers()) {
            for (Worker worker: p.getWorkers()) {
                numAllWorkerCopy --; // decremento il numero totale dei worker il primo è in alto
                if (numAllWorkerCopy > 0){ //è sempre così ma controllo ulteriore
                    posWorker[numAllWorkerCopy][0] = worker.getPosition().getNumRow();
                    posWorker[numAllWorkerCopy][1] = worker.getPosition().getNumColumn();
                    Color color = p.getColor();
                    int numColor = color.ordinal();
                    posWorker[numAllWorkerCopy][2] = numColor;

                }
            }


        }
        this.message = message;
        }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void printBoardCLI(){
        printBoardLine();
        for(int boardRow = 4; boardRow >= 0; boardRow--) {
            for(int cellRow = 0; cellRow < 3; cellRow++) {
                for (int col = 0; col < 5; col++) {
                    if(cellRow == 0 || cellRow == 2) System.out.print("|" + getLevelTD(boardRow, col) + "|");
                    else System.out.print("|" + getWorker(boardRow, col) + "|");
                }
                System.out.print('\n');
            }
            printBoardLine();
        }
    }

    private String getWorker(int row, int col) {
        //TODO per ogni cella controllo se c'è un worker e se si scrivo CW con C che sta per l'iniziale del colore
        for (int rowPosWorker = 0; rowPosWorker < numAllWorker; rowPosWorker++) {
            if (posWorker[rowPosWorker][0] == row) {
                if (posWorker[rowPosWorker][1] == col) {
                    int[] colorList = {Color.Green.ordinal(), Color.Yellow.ordinal(), Color.Red.ordinal()};
                    for (int color : colorList
                    ) {
                        if (posWorker[rowPosWorker][2] == color) {
                            return getLevelLR(row, col) + " " +  color + "W " + getLevelLR(row, col);
                        }
                    }

                }

            }

        } return getLevelLR(row, col) + "    " + getLevelLR(row, col);
    }

    /**
     * return 6 symbols to fill the Top and Down of the board cell
     */
    public String getLevelTD(int row, int col){
        //TODO per ogni cella guardo il livello e metto il simbolo
        //TODO decidere i simboli tra # ^ ' \ / e restituirlo x6
        int levelCell = lv[row][col];
        String level = Integer.toString(levelCell) + levelCell;
        level = level + level + level;

        return level;
    }
    /**
     * return 2 symbols to fill the Left and Right of the board cell
     */
    public int getLevelLR(int row, int col){
        return lv[row][col];
        //TODO per ogni cella guardo il livello e metto il simbolo
        //TODO decidere i simboli tra # ^ ' \ / e restituirlo x2

    }

    public void printBoardLine(){
        System.out.println("+------++------++------++------++------+");
    }

}
