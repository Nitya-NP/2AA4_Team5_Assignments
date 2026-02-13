import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The board class represents the game board in the Catan game
 * This mainly stores the board elements and provides access to them through getter methods.
 * It also manages the turns of players. 
 * 
 * @author Nitya Patel, Ranica Chawla
 */
public class Board {
	private Tile[] tiles; //Tiles on board
	private Node[] nodes; //Nodes on board
	private Roads[] roads; //Roads on board

	/**
	 * Constructs a Board with tiles, nodes and roads
	 * 
	 * @param tiles array of Tile
	 * @param nodes array of Node
	 * @param roads array of Roads
	 */
	public Board() {
		// Initialize the board with 19 tiles and 54 nodes
		this.tiles = new Tile[19];
		this.nodes = new Node[54];

		// Loop to create nodes and assign IDs
		for (int i = 0; i < 54; i++) {
			nodes[i] = new Node(i);
		}

		assignResources();
		connectNodesToTiles();
	}

	private void assignResources() {
		Resources[] resourceOrder = {
			Resources.LUMBER,
			Resources.GRAIN,
			Resources.BRICK,
			Resources.ORE,
			Resources.WOOL,
			Resources.WOOL,
			Resources.WOOL,
			Resources.GRAIN,
			Resources.ORE,
			Resources.LUMBER,
			Resources.ORE,
			Resources.GRAIN,
			Resources.LUMBER,
			Resources.BRICK,
			Resources.BRICK,
			Resources.GRAIN,
			Resources.NOTHING,
			Resources.LUMBER,
			Resources.WOOL
		};
		
		// Loop to create tiles and assign IDs and token numbers
		for (int i = 0; i < 19; i++) {
			tiles[i] = new Tile(resourceOrder[i], i, 0);
		}
	}	
	
	/**
	 * Connects each tile to its nodes
	 */
    private void connectNodesToTiles() {
		// Define a mapping of the tiles to their corresponding nodes
		int[][] tileNodeMapping = {
			{0, 1, 2, 3, 4, 5},
			{1, 2, 9, 8, 7, 6},
			{3, 12, 11, 10, 9, 2},
			{3, 4, 15, 14, 13, 12},
			{4, 15, 17, 18, 16, 5},
			{0, 5, 16, 21, 19, 20},
			{0, 1, 6, 23, 22, 20},
			{7, 8, 27, 26, 25, 24},
			{9, 10, 29, 28, 27, 8},
			{11, 32, 31, 30, 29, 10},
			{13, 34, 33, 32, 11, 12},
			{37, 36, 35, 34, 13, 14},
			{39, 38, 37, 14, 15, 17},
			{42, 41, 39, 17, 18, 40},
			{44, 40, 18, 16, 21, 43},
			{45, 43, 21, 19, 46, 47},
			{46, 19, 20, 22, 49, 48},
			{49, 22, 23, 52, 51, 50},
			{23, 6, 7, 24, 53, 52}
		};

		// Loop to connect each tile to its corresponding nodes based on the mapping
		for (int i = 0; i < 19; i++) {
			// Create an array to hold the nodes for the current tile
			Node[] tileNodes = new Node[6];
			// Loop to assign the nodes to the tile based on the mapping
			for (int j = 0; j < 6; j++) {
				// Get the node index from the mapping and assign the corresponding node to the tile
				tileNodes[j] = nodes[tileNodeMapping[i][j]];
			}
			tiles[i].setNodes(tileNodes);
		}
    }

	/**
	 * @return all tiles on the board
	 */
	public Tile[] getTile() {
		return this.tiles;
	}

	/**
	 * @return all nodes on the board
	 */
	public Node[] getNode() {
		return this.nodes;
	}
	
	/**
	 * @return all roads on the board
	 */
	public Roads[] getRoad() {
		return this.roads;
	}

	/**
	 * Simulates a player's turn
	 * Handles dice roll, resource gain, and random building action.
	 * 
	 * @param player the player whose turn it is 
	 * @param diceValue the dice value (2-12)
	 */
	public void takeTurn(Player player, int diceValue) {
		Random rand = new Random();
	    System.out.println("Player " + player.getPlayerId()+ " rolled " + diceValue);
	    if (diceValue != 7) {
	        Resources[] allResources = Resources.values();
	        Resources r = allResources[rand.nextInt(allResources.length)];
	        player.addResource(r, 1);
	
	        System.out.println("Player " + player.getPlayerId() + " gained 1 " + r);
	    } else {
	        System.out.println("Player " + player.getPlayerId() + " rolled 7 (no resources)");
	    }
	
	    //Choose Random Action
	    int action = rand.nextInt(4); // 0=road, 1=settlement, 2=city, 3=pass
	
	    switch (action) {
	
	        case 0: // Build Road
	            Node[] nodes = getNode();
	            if (nodes.length >= 2) {
	                Node n1 = nodes[rand.nextInt(nodes.length)];
	                Node n2; 
					do{
						n2= nodes[rand.nextInt(nodes.length)];
					}while (n1==n2);
	
	                Node[] roadNodes = { n1, n2 };
	
	                Building road = new Roads(roadNodes, player);
	                player.addBuilding(road);
	
	                System.out.println("Player " + player.getPlayerId() + " built Road");
	            }
	            break;
	
	        case 1: // Build Settlement
	            Building settlement = new Settlement(player);
	            player.addBuilding(settlement);
	            System.out.println("Player " + player.getPlayerId() + " built Settlement");
	            break;
	
	        case 2: // Build City
	            Building city = new Cities(player);
	            player.addBuilding(city);
	            System.out.println("Player " + player.getPlayerId() + " built City");
	            break;
	
	        case 3: // Pass
	            System.out.println("Player " + player.getPlayerId() + " passes");
	            break;
	    }
	}
    	
}







