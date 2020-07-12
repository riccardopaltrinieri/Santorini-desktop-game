package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.View.Graphics.MainFrame;
import it.polimi.ingsw.utils.Color;
import org.json.simple.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class LiteBoard implements Serializable {
    private static final long serialVersionUID = 36347531L;

    private final int[][] levels;     // [row][column] = level

    private final int[][] posWorker;
     /* [worker] [0] = row
     *  [worker] [1] = column
     *  [worker] [2] = color
     *  */

    private final String message;
    private final int numAllWorker;

    /**
     * Constructor used to only send a message
     * @param input is the message to send
     */
    public LiteBoard(String input){
        message = input;
        levels = null;
        posWorker = null;
        numAllWorker = 0;
    }

    /**
     * Constructor used to send all the data needed by clients
     * @param message referred to what just happened
     * @param board from where taking the data on levels
     * @param game from where taking the data on workers
     */
    public LiteBoard(String message, Board board, Game game) {

        this.message = message;
        numAllWorker = game.getNumWorkers();
        levels = new int[board.getNumRow()][board.getNumColumn()];
        posWorker = new int[numAllWorker][3];

        for (Cell[] c: board.getMap()) {
            for (Cell c1:c) {
                int level = c1.getLevel();
                int row = c1.getNumRow();
                int column = c1.getNumColumn();
                levels[row][column] = level;
            }
        }

        int numPosWorker = 0;
        for (Player p : game.getPlayers()) {
            for (Worker worker : p.getWorkers()) {
                posWorker[numPosWorker][0] = worker.getPosition().getNumRow();
                posWorker[numPosWorker][1] = worker.getPosition().getNumColumn();
                posWorker[numPosWorker][2] = p.getColor().ordinal();
                numPosWorker++;
            }
        }
    }

    /**
     * Method which prints the board on the System.out using data passed from the
     * model, like levels of building and position and color of the workers.
     */
    public void printBoardCLI(){
        printBoardLine();
        for(int boardRow = 4; boardRow >= -1; boardRow--) {
            for(int cellRow = 0; cellRow < 3; cellRow++) {
                for (int col = -1; col < 5; col++) {
                    if (col == -1) {
                        // Print the number of the rows on the board left side
                        if (cellRow == 0 || cellRow == 2) System.out.print("      ");
                        else if (boardRow >= 0) System.out.print("  " + (boardRow + 1) + "   ");
                        else System.out.print("      ");

                    } else if (boardRow == -1) {
                        // Print the number of the columns on the bottom
                        if (cellRow == 1) System.out.print("    " + (col + 1) + "    ");
                    } else {
                        // Print the first and third line of the cell using the level number
                        if (cellRow == 0 || cellRow == 2) System.out.print("|" + getLevelTD(boardRow, col) + "|");
                        // Print the second line of the cell using level and the worker if present
                        else System.out.print("|" + getWorker(boardRow, col) + "|");
                    }
                }
                // End of the board row
                System.out.print('\n');
            }
            if(boardRow != -1) printBoardLine();
        }
    }

    /**
     * Return the middle row of the board to print on console with the
     * colored worker if present, empty otherwise
     * @param row of the cell to inspect
     * @param col of the cell to inspect
     * @return a 6 symbols string to print with or without the worker
     */
    private String getWorker(int row, int col) {
        for (int worker = 0; worker < numAllWorker; worker++) {
            if (posWorker[worker][0] == row) {
                if (posWorker[worker][1] == col) {

                    int color = posWorker[worker][2];
                    worker = worker % 2 + 1;    // Show the number of worker that will be 1 or 2

                    return getLevelLR(row, col) + " " + Color.intToFirstLetter(color) + "W" + worker + ' ' + getLevelLR(row, col);
                }
            }
        }
        return getLevelLR(row, col) + "     " + getLevelLR(row, col);
    }

    /**
     * return 7 numbers to fill the Top and Down of the board cell
     */
    private String getLevelTD(int row, int col){
        return String.valueOf(levels[row][col]).repeat(7);
    }
    /**
     * return a number to fill the Left and Right of the board cell
     */
    private String getLevelLR(int row, int col){
        return String.valueOf(levels[row][col]);
    }

    private void printBoardLine(){
        System.out.println("      +-------++-------++-------++-------++-------+");
    }

    /**
     * @return true if the worker in the cell can move, false otherwise
     */
    public boolean workerCanMove(int rowWorker, int colWorker) {

        //check all the cells from the one top-left to the one down-right, if just one is ok the worker can move
        for (int row = rowWorker - 1; row <= rowWorker + 1; row++)
            for (int col = colWorker - 1; col <= colWorker + 1; col++)
                if(0 <= row && row < 5 && 0 <= col && col < 5){
                        if(
                            // should not be the same cell as worker's position
                            (row!=rowWorker)||(col!=colWorker)&&
                            // should be maximum 1 level higher and at level 3
                            (levels[row][col] <= levels[rowWorker][colWorker] + 1) &&
                            (levels[row][col] < 4)) {
                            //should not be the position of another player
                            boolean occupied = false;
                            for (int i = 0; i < numAllWorker;i++) {
                                if ((posWorker[i][0] == row) && (posWorker[i][1] == col)) {
                                    occupied = true;
                                    break;
                                }
                            }
                            if (!occupied) {
                                return true;
                            }
                        }
                }


        return false;
    }

    /**
     * Same as {@link #workerCanMove(int, int)} but giving as parameter the player relative
     * number of the worker and the player's color
     */
    public boolean workerCanMoveCLI(int worker, int color) {
        for (int w = 0; w < numAllWorker; w ++) {
            if (w % 2 == (worker-1) && posWorker[w][2] == color) {
                return workerCanMove(posWorker[w][0], posWorker[w][1]);
            }
        }
        throw new IllegalArgumentException("this worker doesn't exists");
    }

    /**
     * Method used to print levels and workers on the buttons using data
     * passed with the board from the model
     * @param frame where to print the data
     */
    public void printBoardGUI(MainFrame frame){
        int row;
        int column;
        for (int i=0;i<frame.getActiveBoardButtons().length;i++){
            frame.getActiveBoardButtons()[i].setHaveWorker(false);
            frame.getActiveBoardButtons()[i].setWorkerColorNull();
            frame.getActiveBoardButtons()[i].setWorkerNum(-1);
            row = (i/5);
            column = (i%5);
            frame.getActiveBoardButtons()[i].setLevel(levels[row][column]);
            frame.getActiveBoardButtons()[i].setEnabled(false);
            frame.getActiveBoardButtons()[i].setSelectableCell(false);
        }
        for (int j =0; j<numAllWorker;j++) {
            int index = (posWorker[j][0]) * 5 + posWorker[j][1];
            frame.getActiveBoardButtons()[index].setHaveWorker(true);
            frame.getActiveBoardButtons()[index].setWorkerColor(Color.intToColor(posWorker[j][2]));
            frame.getActiveBoardButtons()[index].setWorkerNum(j);
        }
        for (int i=0; i<25;i++){
            frame.getActiveBoardButtons()[i].repaint();
        }
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        JSONArray json = new JSONArray();
        json.add(message);

        for (int i=0;i<5;i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j=0;j<5;j++) {
                if(levels != null) row.add(levels[i][j]);
                else row.add(0);
            }
            json.add(row);
        }

        for (int i=0;i<numAllWorker;i++) {
            ArrayList<Integer> worker = new ArrayList<>();
            worker.add(posWorker[i][0]);
            worker.add(posWorker[i][1]);
            worker.add(posWorker[i][2]);
            json.add(worker);
        }

        return json.toString();
    }
}
