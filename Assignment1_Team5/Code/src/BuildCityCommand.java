/**
 * Class to build city
 * 
 * @author Raadhikka Gupta, Ranica Chawla
 */

public class BuildCityCommand implements Command{
    private Board board;
    private Player player;
    private int nodeId;

    public BuildCityCommand(Board board, Player player, int nodeId) {
        this.board = board;
        this.player = player;
        this.nodeId = nodeId;
    }

    @Override
    public void execute() {
        board.buildCity(player, nodeId);
    }

    @Override
    public void undo() {
        board.removeCity(player, nodeId); 
    }

    @Override
    public void redo() {
        execute();
    }
}