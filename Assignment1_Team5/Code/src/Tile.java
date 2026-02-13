/**
 * A Tile class that represents a tile on the board. 
 * It has a resource, an ID, and a token number. 
 */
public class Tile {
	// Private attributes of the tile class
	private int tileID;
	private int token;
	private Resources resource;
	private Node[] nodes;

	/**
	 * Initializes a tile with the given resource, ID, and token number
	 * @param resource The resource that the tile holds
	 * @param id The tile's ID number
	 * @param token The token number that is associated with the tile
	 */
	public Tile(Resources resource, int id, int token) {
		// Initialize private attributes with the given parameters
		this.resource = resource;
		this.tileID = id;
		this.token = token;
		nodes = new Node[6];
	}

	/**
	 * @return the resource that the tile holds
	 */
	public Resources getResource() {
		return this.resource;
	}

	/**
	 * @return the tile's ID number
	 */
	public int getTileId() {
		return this.tileID;
	}

	/**
	 * @return the token number that is associated with the tile
	 */
	public int getToken() {
		return this.token;
	}

	/**
	 * @return the nodes that are adjacent to the tile
	 */
	public Node[] getNodes() {
		return this.nodes;
	}

	/**
	 * Sets the nodes that are adjacent to the tile
	 * @param nodes An array of nodes that are adjacent to the tile
	 */
	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}
}
