/**
 * Class to build settlements
 * 
 * @author Raadhikka Gupta, Ranica Chawla
 */


public class BuildSettlementCommand implements Command {
    private Board board;
    private Player player;
    private int nodeId;

    public BuildSettlementCommand(Board board, Player player, int nodeId) {
        this.board = board;
        this.player = player;
        this.nodeId = nodeId;
    }

    @Override
    public void execute() {
        board.buildSettlement(player, nodeId);
    }

    @Override
    public void undo() {
        board.removeSettlement(player, nodeId); 
    }

    @Override
    public void redo() {
        execute();
    }
}