
import java.util.Random;

/**
 * The board class represents the game board in the Catan game
 * This mainly stores the board elements and provides access to them through
 * getter methods.
 * It also manages the turns of players.
 * 
 * @author Nitya Patel, Ranica Chawla, Raadhikka Gupta, Krisha Patel
 */
public class Board {
    private Tile[] tiles; // Tiles on board
    private Node[] nodes; // Nodes on board
    private Road[] roads; // Roads on board

    private GameLogger logger; // Logger for game events
    private RobberActionsManager robberManager; // Robber on board
    private final Random rand = new Random();

    /**
     * Constructs a Board with tiles, nodes and roads
     * 
     * @param tiles array of Tile
     * @param nodes array of Node
     * @param roads array of Roads
     */
    public Board(GameLogger logger) {
        // Initialize the board with 19 tiles and 54 nodes
        this.tiles = new Tile[19];
        this.nodes = new Node[54];
        this.roads = new Road[72];

        this.logger = logger; // Initialize the logger

        // Loop to create nodes and assign IDs
        for (int i = 0; i < 54; i++) {
            nodes[i] = new Node(i);
        }

        assignResources();
        connectNodesToTiles();
        connectAdjacentNodes();
    }

    /**
     * To set the robber manager up
     * 
     * @param robberManager robber manager
     */
    public void setRobberManager(RobberActionsManager robberManager) {
        this.robberManager = robberManager;
    }

    /**
     * Assign resources to each tile
     */
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

        int[] tokenNum = { 5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11, 0 };
        // Loop to create tiles and assign IDs and token numbers
        for (int i = 0; i < 19; i++) {
            tiles[i] = new Tile(resourceOrder[i], i, tokenNum[i]);
        }
    }

    /**
     * Connects each tile to its nodes
     */
    private void connectNodesToTiles() {
        // Define a mapping of the tiles to their corresponding nodes
        int[][] tileNodeMapping = {
                { 0, 1, 2, 3, 4, 5 },
                { 1, 2, 9, 8, 7, 6 },
                { 3, 12, 11, 10, 9, 2 },
                { 3, 4, 15, 14, 13, 12 },
                { 4, 15, 17, 18, 16, 5 },
                { 0, 5, 16, 21, 19, 20 },
                { 0, 1, 6, 23, 22, 20 },
                { 7, 8, 27, 26, 25, 24 },
                { 9, 10, 29, 28, 27, 8 },
                { 11, 32, 31, 30, 29, 10 },
                { 13, 34, 33, 32, 11, 12 },
                { 37, 36, 35, 34, 13, 14 },
                { 39, 38, 37, 14, 15, 17 },
                { 42, 41, 39, 17, 18, 40 },
                { 44, 40, 18, 16, 21, 43 },
                { 45, 43, 21, 19, 46, 47 },
                { 46, 19, 20, 22, 49, 48 },
                { 49, 22, 23, 52, 51, 50 },
                { 23, 6, 7, 24, 53, 52 }
        };

        // Loop to connect each tile to its corresponding nodes based on the mapping
        for (int i = 0; i < 19; i++) {
            // Create an array to hold the nodes for the current tile
            Node[] tileNodes = new Node[6];
            // Loop to assign the nodes to the tile based on the mapping
            for (int j = 0; j < 6; j++) {
                // Get the node index from the mapping and assign the corresponding node to the
                // tile
                tileNodes[j] = nodes[tileNodeMapping[i][j]];
            }
            tiles[i].setNodes(tileNodes);
        }
    }

    /**
     * Connects each node on the board to its adjacent nodes.
     */
    private void connectAdjacentNodes() {
        // Define adjacency for each node
        int[][] adjacency = {
                { 1, 2, 3 },
                { 0, 2, 4 },
                { 0, 1, 5 },
                { 0, 5, 6 },
                { 1, 5, 7 },
                { 2, 3, 4, 6 },
                { 3, 5, 8 },
                { 4, 5, 9 },
                { 6, 9, 10 },
                { 7, 8, 11 },
                { 8, 11 },
                { 9, 10 }
        };

        // Add adjacent nodes
        for (int i = 0; i < adjacency.length; i++) {
            for (int neighborIndex : adjacency[i]) {
                nodes[i].addAdjacentNode(nodes[neighborIndex]);
            }
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
    public Road[] getRoad() {
        return this.roads;
    }

    private boolean settlementDistance(Node node) {
        for (Node n : node.getAdjacentNodes()) {
            if (n.isOccupied()) {
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
        if ((n1.isOccupied() && n1.getBuilding().getOwner() == player)
                || (n2.isOccupied() && n2.getBuilding().getOwner() == player))
            return true;

        // check if the road is connected to an exiting player owned road
        for (Road road : roads) {
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
    private void addRoadToBoard(Road road) {
        for (int i = 0; i < roads.length; i++) {
            if (roads[i] == null) {
                roads[i] = road;
                break;
            }
        }
    }

    /**
     * Handles resouce production for the player based on the dice roll
     * if dice value is 7, no resouce are gained
     * 
     * @param player    the player gaining resource
     * @param diceValue dice roll value
     */
    public void produceResource(Player player, int diceValue) {
        if (diceValue == 7) {
            logger.log(player.getPlayerId(), "rolled 7 (no resources)");
            return;
        }

        for (Tile t: tiles) {
            if (shouldSkipTile(t, diceValue)) {
                continue;
            }
            processTileForResource(t, diceValue);
        }
    }

    /**
     * Determines if a Tile should be skipped during resoruce production
     */
    private boolean shouldSkipTile(Tile t, int diceValue) {
        return robberManager.isRobberOnTile(t) || t.getToken() != diceValue;
    }

    /**
     * Processes resource production for a tile
     * Each building adjacent to the tile produces resources
     * @param t Tile
     * @param diceValue dice roll value
     */
    private void processTileForResource(Tile t, int diceValue) {
        for (Node n : t.getNodes()) {
            if (n.isOccupied()) {
                distributeResourceToPlayer(n, t.getResource());
            }
        }
    }

    /**
     * Gives resources to the player owning the building on the node
     * settlements produce 1 resource, cities produce 2.
     * @param n Node 
     * @param r Resource
     */
    private void distributeResourceToPlayer(Node n, Resources r) {
        Player owner = n.getBuilding().getOwner();
        Building b = n.getBuilding();

        int amount = (b instanceof City) ? 2 : 1;
        owner.addResource(r, amount);

        logger.log(owner.getPlayerId(), "gained " + amount + " " + r + " from Node " + n.getNodeId());
    }

    /**
     * Helper method
     * Attempts to build a road for the player.
     * Randomly selects two nodes and checks if the road can be legally built.
     * adds road to the board if successful and in the player's building
     * 
     * @param player the player
     */
    public void buildRoad(Player player) {

        // Check if player has that resources to build road
        if (!player.hasResources(Resources.BRICK, 1) ||
                !player.hasResources(Resources.LUMBER, 1)) {

            logger.log(player.getPlayerId(), "Not enough resources for road");
            return;
        }

        Node[] allNodes = getNode();
        Node n1;
        Node n2;
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

            // remove resources
            player.removeResource(Resources.BRICK, 1);
            player.removeResource(Resources.LUMBER, 1);

            Road road = new Road(new Node[] { n1, n2 }, player);
            player.addRoad();
            addRoadToBoard(road);
            logger.log(player.getPlayerId(),
                    "built Road between Node " + n1.getNodeId() + " and Node " + n2.getNodeId());
        } else {
            logger.log(player.getPlayerId(), "failed to build a valid road.");
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
    public void buildCity(Player player) {
        // check if player has this resources to build city
        if (!player.hasResources(Resources.ORE, 3) ||
                !player.hasResources(Resources.GRAIN, 2)) {

            logger.log(player.getPlayerId(), "Not enough resources for city");
            return;
        }

        Node n = getNode()[rand.nextInt(nodes.length)];
        if (n.isOccupied() && n.getBuilding() instanceof Settlement && n.getBuilding().getOwner() == player) {

            player.removeResource(Resources.ORE, 3);
            player.removeResource(Resources.GRAIN, 2);

            Building city = new City(player);
            n.setBuilding(city);
            player.addBuilding(city);
            logger.log(player.getPlayerId(), "upgraded to City at Node " + n.getNodeId());
        } else {
            logger.log(player.getPlayerId(), "City upgrade failed.");
        }

    }

    /**
     * Attempts to build a Settlement for the player.
     * Randomly selects a node and checks if it is empty.
     * adds the settlement to the player's buildings and the board
     * 
     * @param player
     */
    public void buildSettlement(Player player) {

        // check if player has this resources to build settlement
        if (!player.hasResources(Resources.BRICK, 1) ||
                !player.hasResources(Resources.LUMBER, 1) ||
                !player.hasResources(Resources.WOOL, 1) ||
                !player.hasResources(Resources.GRAIN, 1)) {

            logger.log(player.getPlayerId(), "Not enough resources for settlement");
            return;
        }

        Node n = getNode()[rand.nextInt(nodes.length)];
        if (!n.isOccupied() && !settlementDistance(n)) {

            // remove resources
            player.removeResource(Resources.BRICK, 1);
            player.removeResource(Resources.LUMBER, 1);
            player.removeResource(Resources.WOOL, 1);
            player.removeResource(Resources.GRAIN, 1);

            Building settlement = new Settlement(player);
            n.setBuilding(settlement);
            player.addBuilding(settlement);
            logger.log(player.getPlayerId(), "built Settlement at Node " + n.getNodeId());
        } else {
            logger.log(player.getPlayerId(), "Settlement failed");
        }

    }

}
