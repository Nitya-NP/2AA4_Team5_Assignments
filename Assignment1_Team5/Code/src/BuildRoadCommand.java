/**
 * Class to build road
 * 
 * @author Raadhikka Gupta, Ranica Chawla
 */

public class BuildRoadCommand implements Command {
    private Board board;
    private Player player;
    private int nodeId1;
    private int nodeId2;

    public BuildRoadCommand(Board board, Player player, int nodeId1, int nodeId2) {
        this.board = board;
        this.player = player;
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
    }

    @Override
    public void execute() {
        board.buildRoad(player, nodeId1, nodeId2);
    }

    @Override
    public void undo() {
        board.removeRoad(player, nodeId1, nodeId2);
    }

    @Override
    public void redo() {
        execute();
    }
}