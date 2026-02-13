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
 	
	private boolean settlementDistance(Node node){
        for(Node n: node.getAdjacentNodes()){
            if(n.isOccupied()){
                return true;
            }
        }
        return false; 
    }
    /**
     * Checks if a road connecting two nodes can be built by a player.
     * The proposed road or building is already connected with player's exiting
     * road.
     * 
     * @param player the player attempting to build
     * @param n1     the first node of proposed road
     * @param n2     second node of the proposed road
     * @return true if the road can be built, false otherwise
     */
    private boolean isRoadConnected(Player player, Node n1, Node n2) {
        // Check if either node has a player owned building
        if ((n1.isOccupied() && n1.getBuilding().getOwner() == player) || (n2.isOccupied() && n2.getBuilding().getOwner() == player))
            return true;

        // check if the road is connected to an exiting player owned road
        for (Roads road : roads) {
            if (road != null && road.getOwner() == player && (road.isConnected(n1) || road.isConnected(n2))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new road to the board's road array
     * add the road to the empty slot.
     * 
     * @param road the road to add
     */
    private void addRoadToBoard(Roads road) {
        for (int i = 0; i < roads.length; i++) {
            if (roads[i] == null) {
                roads[i] = road;
                break;
            }
        }
    }

    /**
     * Simulates the player's turn
     * Handles dice roll, resource production and builds a raod, settlement, or city
     * If player has more than 7 resources, only building actions are attempted
     * 
     * @param player    the player whose turn it is
     * @param diceValue the dice value (2-12)
     */
    public void takeTurn(Player player, int diceValue) {
        Random rand = new Random();
        System.out.println("Player " + player.getPlayerId() + " rolled " + diceValue);

        // Resource gain
        produceResource(player, diceValue);

        // Determine action
        int action = (player.getTotalResources() > 7) ? rand.nextInt(3) : rand.nextInt(4);

        if (action == 0)
            buildRoad(player); // Build Road
        else if (action == 1)
            buildSettlement(player); // Build Settlement
        else if (action == 2)
            buildCity(player); // Build City
        else
            System.out.println("Player " + player.getPlayerId() + " passes"); // Pass
    }

    /**
     * Helper method
     * Handles resouce production for the player based on the dice roll
     * if dice value is 7, no resouce are gained
     * 
     * @param player    the player gaining resource
     * @param diceValue dice roll value
     */
    private void produceResource(Player player, int diceValue) {
        Random rand = new Random();
        if (diceValue != 7) {
            Resources[] allResources = Resources.values();
            Resources r = allResources[rand.nextInt(allResources.length)];
            player.addResource(r, 1);
            System.out.println("Player " + player.getPlayerId() + " gained 1 " + r);
        } else {
            System.out.println("Player " + player.getPlayerId() + " rolled 7 (no resources)");
        }
    }

    /**
     * Helper method
     * Attempts to build a road for the player.
     * Randomly selects two nodes and checks if the road can be legally built.
     * adds road to the board if successful and in the player's building
     * 
     * @param player the player
     */
    private void buildRoad(Player player) {
        Random rand = new Random();
        Node[] allNodes = getNode();
        Node n1, n2;
        boolean validRoad = false;
        int attempts = 0;

        do {
            n1 = allNodes[rand.nextInt(allNodes.length)];
            n2 = allNodes[rand.nextInt(allNodes.length)];
            attempts++;
            if (n1 != n2 && isRoadConnected(player, n1, n2)) {
                validRoad = true;
                break;
            }
        } while (attempts < 10);

        if (validRoad) {
            Roads road = new Roads(new Node[] { n1, n2 }, player);
            player.addBuilding(road);
            addRoadToBoard(road);
            System.out.println("Player " + player.getPlayerId() + " built Road between Node " + n1.getNodeId()+ " and Node " + n2.getNodeId());
        } else {
            System.out.println("Player " + player.getPlayerId() + " failed to build a valid road.");
        }

    }

    /**
     * helper method
     * Attempts to build a City for the player by upgrading the settlement.
     * Randomly selects a node and checks if it is settlement.
     * upgrades the settlement into city to the player's buildings and the board
     * 
     * @param player
     */
    private void buildCity(Player player) {
        Random rand = new Random();
        Node n = getNode()[rand.nextInt(nodes.length)];
        if (n.isOccupied() && n.getBuilding() instanceof Settlement && n.getBuilding().getOwner()==player)  {
            Building city = new Cities(player);
            n.setBuilding(city);
            player.addBuilding(city);
            System.out.println("Player " + player.getPlayerId() + " upgraded to City at Node " + n.getNodeId());
        } else {
            System.out.println("City upgrade failed.");
        }

    }

    /**
     * Attempts to build a Settlement for the player.
     * Randomly selects a node and checks if it is empty.
     * adds the settlement to the player's buildings and the board
     * 
     * @param player
     */
    private void buildSettlement(Player player) {
        Random rand = new Random();
        Node n = getNode()[rand.nextInt(nodes.length)];
        if (!n.isOccupied() && !settlementDistance(n)) {
            Building settlement = new Settlement(player);
            n.setBuilding(settlement);
            player.addBuilding(settlement);
            System.out.println("Player " + player.getPlayerId() + " built Settlement at Node " + n.getNodeId());
        } else {
            System.out.println("Settlement failed");
        }

    }


}













