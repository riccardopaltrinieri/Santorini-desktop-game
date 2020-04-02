package it.polimi.ingsw.Model;

public class Cell {
    private int numRow;
    private int numColumn;
    private int level;
    private boolean isEmpty;

    public Cell (int x, int y){
        this.numColumn=x;
        this.numRow=y;
        this.isEmpty=true;
        this.level=0;
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

    //metodi

    public void setEmptyDefault() {
        this.isEmpty = true;
    }

    public boolean canMoveTo(Cell destination) {
        if ((destination.getNumRow()>=this.getNumRow()-1)&&(destination.getNumRow()<=this.getNumRow()+1)&&(destination.getNumColumn()>=this.getNumColumn()-1)&&(destination.getNumColumn()<=this.getNumColumn()+1)&&(destination.isEmpty())&&(destination.level<=this.level+1)){
            return true;
        }
        else{
            return false;
        }
    }

}
