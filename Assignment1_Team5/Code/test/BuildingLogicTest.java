import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Building logic test cases 
 * 1. Place a city
 * 2. Place a settlement
 * 3. Place a road
 * 4. Validation case: Nodes start empty
 * @author Ranica Chawla
 */
public class BuildingLogicTest {

    /**
     * To test that placing a settlement on a node correctly marks the node as occupied and 
     * assigns ownership to the correct player.
     */
	@Test
    public void placeSettlementOnBoard() {
        // Build board and player
        Board board = new Board(new GameLogger());
        Player player = new Player(1);

        // Construct new settlement
		Building settlement = new Settlement(player);

        // Place settlement on first node
        Node[] nodes = board.getNode();
        nodes[0].setBuilding(settlement);

        // Check that node contains a settlement
        assertTrue(nodes[0].isOccupied() && nodes[0].getBuilding() instanceof Settlement);
        // Test the settlement is owned by the player
        assertEquals(player, nodes[0].getBuilding().getOwner());
    
    }

    /**
     * To test that placing a city on a node correctly marks the node as occupied and 
     * assigns ownership to the correct player.
     */
    @Test
    public void placeCityOnBoard() {
        // Build board and player
        Board board = new Board(new GameLogger());
        Player player = new Player(1);

        // Construct new city
		Building city = new Cities(player);

        // Place city on first node
        Node[] nodes = board.getNode();
        nodes[0].setBuilding(city);

        // Check that node contains a city
        assertTrue(nodes[0].isOccupied() && nodes[0].getBuilding() instanceof Cities);
        // Test the city is owned by the player
        assertEquals(player, nodes[0].getBuilding().getOwner());

    }

    /**
     * To test that placing a road on a node correctly marks the node as occupied and 
     * assigns ownership to the correct player.
     */
    @Test
    public void placeRoadOnBoard() {
        // Build board and player
        Board board = new Board(new GameLogger());
        Player player = new Player(1);

        // Construct new road
        Node[] nodes = board.getNode();
		Roads road = new Roads(new Node[] { nodes[0], nodes[1] }, player);

        // Place road on board
        board.getRoad()[0] = road;

        // Check that the correct nodes contains the road
        assertTrue(board.getRoad()[0].isConnected(nodes[0]));
        assertTrue(board.getRoad()[0].isConnected(nodes[1]));
        // Test the road is owned by the player
        assertEquals(player, board.getRoad()[0].getOwner());
    }
}