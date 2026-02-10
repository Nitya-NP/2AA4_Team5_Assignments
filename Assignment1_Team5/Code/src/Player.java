
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * The Player classs represents an player participating in Catan simulation.
 * A Player owns resources, buildings, and winning points, and can take a turn.
 * It track resources, track buildings owned, track winning points and exceute a random action during a turn
 * 
 * @author Nitya Patel
 */
public class Player {
	
	private int playerId; //Unique identifier of the player
	private int points; //Current winning points
	private Map<Resources, Integer> resources; //Stores the number of resources player owns
	private Map<Class<? extends Building>, Integer> buildings; //Stores buildings owned by the player (Roads,Settlement, Cities)
	
	/**
	 * Constructs a new Player with empty resources and buildings
	 * 
	 * @param playerId the player's unique ID
	 */
	public Player(int playerId) {
		this.playerId=playerId;
		this.points=0;
		this.resources=new HashMap<>();
		this.buildings=new HashMap<>();
		initResources();
		initBuildings();
	}
	
	/**
	 * Initializes all resources to 0
	 */
	private void initResources() {
		for(Resources r: Resources.values()) {
			this.resources.put(r, 0);
		}
	}
	
	/**
	 * Initializes building counts to 0
	 */
	private void initBuildings() {
		buildings.put(Roads.class,0); 
		buildings.put(Settlement.class,0);
		buildings.put(Cities.class,0);

	}
	
	/**
	 * @return the player's id
	 */
	public int getPlayerId() {
		return this.playerId ;
	}
	
	/**
	 *  @return the player's winning points
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**
	 * Adds points to the player
	 */
	public void addPoints(int points) {
		this.points+=points;
	}
	
	/**
	 *  @return the amount of specific resource
	 */
	public int getResource(Resources resource) {
		return resources.getOrDefault(resource,0);
	}
	
	/**
	 * Adds resources to player
	 */
	public void addResource(Resources res, int amount) {
		resources.put(res, resources.getOrDefault(res, 0)+amount);
	}
	
	/**
	 * Removes resources if possible
	 *  @return either true or false 
	 */
	public boolean removeResource(Resources res, int amount) {
		int cur= resources.getOrDefault(res, 0);
		if(cur>=amount) {
			resources.put(res, cur-amount);
			return true;
		}
		return false;
	}

	/**
	 * Adds a building to the player based on its class name
	 * Also updates points where applicable
	 */
	public void addBuilding(Building buil) {
		Class<? extends Building> type = buil.getClass();
		buildings.put(type,buildings.getOrDefault(type,0)+1);
		
		//Add points according to rules
		if(buil instanceof Settlement) {
			addPoints(1);
		}
		else if(buil instanceof Cities) {
			addPoints(2);
		}
		
	}
	
	/**
	 *  @return how many buildings of given type the player owns
	 */
	public int getBuilding(Class<? extends Building> type) {
		return buildings.getOrDefault(type, 0);
	}
	
	/**
	 *  @return a summary of the player
	 */
	public String toString() {
		return "Player "+ playerId + "| Points: "+ points + "| Resources: " +resources + "| Buildings: " +buildings;
	}
	
	/**
	 * Simulater a player's turn
	 * 
	 * 0: gain random resource
	 * 1: build a random building
	 * 2: pass
	 * 
	 * actions taken during the round
	 * 
	 * @param roundNum current round number
	 * @param nodes a list of available nodes on the board that buildings can use
	 * 
	 */
	public void takeTurn(int roundNum, Node []nodes) {
		Random rand= new Random();
		int action= rand.nextInt(3);
		

		switch (action) {
			case 0:
				//Gain a random resource
				Resources[] allResources= Resources.values();
				Resources r= allResources[rand.nextInt(allResources.length)];
				addResource(r, 1);
				System.out.println(roundNum + ": Player " + playerId+" have gained 1 " +r);
				break;
			
			case 1:
				//Build a random building
				Building buil;
				int choice= rand.nextInt(3);
				
				//change it 
				if(choice ==0 && nodes.length>=2){

					//Select 2 random nodes for the road
					Node n1 = nodes[rand.nextInt(nodes.length)];
                	Node n2 = nodes[rand.nextInt(nodes.length)];
                	Node[] roadNodes = { n1, n2 };

					buil= new Roads(roadNodes, this);
				}
				else if(choice ==1) buil= new Settlement(this);
				else buil= new Cities(this);
						
				addBuilding(buil);
				System.out.println(roundNum + ":Player " +playerId+ " built a " + buil.getClass().getSimpleName());
				break;
				
			case 2: 
				System.out.println(roundNum+ ":Player "+playerId+ " passes");
				break;
	
			default:
				return;
		}
		
		
	}
}
