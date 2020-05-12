package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Board;

public class LiteBoard {
    private int[][] lv; // [riga][colonna] = livello
    /* [worker] [0] = riga
     * [worker] [1] = colonna
     * [worker] [2] = COLORE (USATE PAINT )*/
    private int[][] posworker;
    private String message;

    public LiteBoard(String message, Board board) {
        // TODO creare costruttore che riempia la liteboard a partire dalla board e dai player
        // TODO Aggiungere i player nei parametri
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
        for(int boardRow = 0; boardRow < 5; boardRow++) {
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
        return getLevelLR(row, col) + "  " + getLevelLR(row, col);
    }
    /**
     * return 6 symbols to fill the Top and Down of the board cell
     */
    public String getLevelTD(int row, int col){
        //TODO per ogni cella guardo il livello e metto il simbolo
        //TODO decidere i simboli tra # ^ ' \ / e restituirlo x6

        return "      ";
    }
    /**
     * return 2 symbols to fill the Left and Right of the board cell
     */
    public String getLevelLR(int row, int col){
        //TODO per ogni cella guardo il livello e metto il simbolo
        //TODO decidere i simboli tra # ^ ' \ / e restituirlo x2
        return "  ";
    }

    public void printBoardLine(){
        System.out.println("+------++------++------++------++------+");
    }

}
