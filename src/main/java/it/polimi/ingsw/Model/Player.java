package it.polimi.ingsw.Model;

public class Player {

    //attributi
    private String name;
    private Color color;
    private Turn turn;
    private Divinity divinity;
    private Boolean canMoveUp;
    private Worker[] workerArray = new Worker[2];
    private Game game;

    //getter e setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Divinity getDivinity() {
        return divinity;
    }

    public void setDivinity(Divinity divinity) {
        this.divinity = divinity;
    }

    public Boolean getCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public Worker[] getWorkerArray() {
        return workerArray;
    }

    public void setWorkerArray(Worker[] workerArray) {
        this.workerArray = workerArray;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
