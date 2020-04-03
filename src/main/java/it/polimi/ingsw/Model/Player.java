package it.polimi.ingsw.Model;

public class Player {

    private String name;
    private Color color;
    private Turn turn;
    private Divinity divinity;
    private Boolean canMoveUp;
    private Worker[] workers;
    private Game game;

    /**
     * Constructor
     */
    public Player (String name, Color color, Game game) {
        this.name = name;
        this.color = color;
        this.game = game;
        this.workers = new Worker[2];

    }

    /**
     * Place the workers on the map with the worker constructor
     */
    public void placeWorkers(int row, int column, int numWorker) {
        Board board = this.game.getBoard();
        workers[numWorker] = new Worker( board.getCell(row,column), this);
        board.getCell(row, column).setEmpty(false);
    }

    /**
     * Examine the cell around the player's workers
     * @return boolean that indicates if the player can make any move
     */
    public boolean canMove() {
        Board board = this.game.getBoard();

        for(int i = 0; i < 2; i++) {
            Cell pos = workers[i].getPosition();
            //check all the cells from the one top-left to the one down-right
            for (int row = pos.getNumRow() - 1; row <= pos.getNumRow() + 1; row++)
                for (int col = pos.getNumColumn() - 1; col <= pos.getNumColumn() + 1; col++)
                    if (pos.canMoveTo(board.getCell(row,col))) return true;
        }
        this.lose();
        return false;
    }

    /**
     * The player has win because his worker has moved on a level 3 building
     */
    public void win() {
        this.game.hasWinner();
    }

    /**
     * The player has lost because he can't make any move
     */
    public void lose() {
        this.game.hasLoser();
    }


    //***************** GETTER AND SETTER ******************

    public String getName() {
        return name;
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
    public Worker getWorker(int numWorker) {
        return workers[numWorker];
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
