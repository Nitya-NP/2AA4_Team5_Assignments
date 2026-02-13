
import java.util.HashMap;
import java.util.Map;

/**
 * The Player classs represents an player participating in Catan simulation.
 * A Player owns resources, buildings, and winning points, and can take a turn.
 * It track resources, track buildings owned, track points and exceute a random
 * action during a turn
 * 
 * @author Nitya Patel
 */
public class Player {

	private int playerId; // Unique identifier of the player
	private int points; // Current points
	private Map<Resources, Integer> resources; // Stores the number of resources player owns
	private Map<Class<? extends Building>, Integer> buildings; // Stores buildings owned by the player (Roads,Settlement, Cities)

	/**
	 * Constructs a new Player with empty resources and buildings
	 * 
	 * @param playerId the player's unique ID
	 */
	public Player(int playerId) {
		this.playerId = playerId;
		this.points = 0;
		this.resources = new HashMap<>();
		this.buildings = new HashMap<>();
		initResources();
		initBuildings();
	}

	/**
	 * Initializes all resources to 0 at the start
	 */
	private void initResources() {
		for (Resources r : Resources.values()) {
			this.resources.put(r, 0);
		}
	}

	/**
	 * Initializes building (Road, City, Settlement) counts to 0 at the start
	 */
	private void initBuildings() {
		buildings.put(Roads.class, 0);
		buildings.put(Settlement.class, 0);
		buildings.put(Cities.class, 0);

	}

	/**
	 * @return the player's id
	 */
	public int getPlayerId() {
		return this.playerId;
	}

	/**
	 * @return the player's points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Adds points to the player
	 * 
	 * @param points number of points to add
	 */
	public void addPoints(int points) {
		this.points += points;
	}



	/**
	 * @return total number of resources the player owns
	 */
	public int getTotalResources() {
		int total = 0;
		for (Integer amount : resources.values()) {
			total += amount;
		}
		return total;
	}

	/**
	 * Adds specific resources to the player
	 * 
	 * @param resource new resource to add
	 * @param amount   the number of units to add
	 */
	public void addResource(Resources resource, int amount) {
		resources.put(resource, resources.getOrDefault(resource, 0) + amount);
	}


	/**
	 * Adds a building to the player based on its class name
	 * Also updates points where applicable
	 * 
	 * @param building
	 */
	public void addBuilding(Building building) {
		Class<? extends Building> type = building.getClass();
		buildings.put(type, buildings.getOrDefault(type, 0) + 1);

		// Add points according to rules
		if (building instanceof Settlement) {
			addPoints(1);
		} else if (building instanceof Cities) {
			addPoints(2);
		}

	}


}
