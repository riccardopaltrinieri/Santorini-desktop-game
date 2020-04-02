package it.polimi.ingsw.Model;

public class Player {

    private String name;
    private Color color;
    private Turn turn;
    private Divinity divinity;
    private Boolean canMoveUp;
    private Worker[] workers = new Worker[2];
    private Game game;


    /**
     * Place the workers on the map with the worker constructor
     */
    public void placeWorkers(int row, int column) {
        Board board = this.game.getBoard();
        Worker worker = new Worker( board.getMap()[row][column], this);
    }

    /**
     * Examine the cell around the player's workers
     * @return boolean that indicates if the player can make any move
     */
    public boolean canMove() {
        boolean cannotMove = true;
        Board board = this.game.getBoard();

        for(int i = 0; i < 2; i++) {
            Cell pos = workers[i].getPosition();
            for (int row = pos.getNumRow() - 1; row < pos.getNumRow() + 1; row++)
                for (int col = pos.getNumColumn() - 1; col < pos.getNumColumn() + 1; col++)
                    if (pos.canMoveTo(board.getMap()[row][col])) cannotMove = false;
        }

        if (cannotMove) this.lose();
        return !cannotMove;
    }

    /**
     * The player has lost because he can't make any move
     */
    public void lose() {
        this.game.hasLoser();
    }

    /**
     * The player has win because his worker has moved on a level 3 building
     */
    public void win() {
        this.game.hasWinner();
    }



    //***************** GETTER AND SETTER ******************

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
        return workers;
    }

    public void setWorkerArray(Worker[] workerArray) {
        this.workers = workerArray;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}
