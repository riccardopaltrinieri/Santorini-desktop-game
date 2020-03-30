package it.polimi.ingsw.Model;

public class Cell {
    private int numRow;
    private int numColumn;
    private int level;
    private boolean isEmpty;



    public int getNumRow(Cell cell){
        return cell.numRow;
    }
    public int getNumColumn(Cell cell){
        return cell.numColumn;
    }
    public int getLevel(Cell cell){
        return cell.level;
    }
    public boolean isEmpty(Cell cell){
        return cell.isEmpty;
    }

    public void getWorker(Cell cell){

    }


}
