package it.polimi.ingsw.Model;

public class Cell {
    private int numRow;
    private int numColumn;
    private int level;
    private boolean isEmpty;

    public void setEmptyDefault() {
        this.isEmpty = false;
    }
    public void setNumRow(int i){
        this.numRow=i;
    }
    public void setNumColumn(int j){
        this.numColumn=j;
    }
    public int getNumRow(){
        return this.numRow;
    }
    public int getNumColumn(){
        return this.numColumn;
    }
    public int getLevel(){
        return this.level;
    }
    public boolean isEmpty(){
        return this.isEmpty;
    }

    public void getWorker(Cell cell){

    }


}
