package it.polimi.ingsw.Model;

public class Player {

    //attributi
    private String name;
    private Color color;
    private Turn turn;
    private Divinity divinity;
    private Boolean canMoveUp;
    private Worker[] workers = new Worker[2];
    private Game game;


    /**
     * Place the workers on the map
     */
    public void placeWorkers(int row, int column) {
        Board board = this.game.getBoard();
        Worker worker = new Worker( board.getMap()[row][column], this);
    }

    /*public int possibleMove() {
        for (int i = 0; i < 2; i++) {
            Cell position = workers[0].getPosition();

        }
    }*/


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

    public Worker[] getWorkers() {
        return workers;
    }

    public void setWorkerArray(Worker[] workers) {
        this.workers = workers;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
