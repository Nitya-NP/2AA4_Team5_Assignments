
/**
 * Abstract class representing a Building in the game.
 * 
 * Building serves as the foundation for all types of buildings 
 * such as Settlements and Cities. Each building is owned by a Player
 * and has a specific point value associated with it.
 *
 *  @author Krisha Patel
 */
public abstract class Building {
	/**
	 * 
	 */
	private Player owner;
	
	/**
	 * 
	 * @param owner - the player who owns the building
	 */
	public Building(Player owner) {
		this.owner = owner;
	}

	/**
	 * 
	 * @return owner - the player who owns the building
	 */
	public Player getOwner() {
		return owner;
	}
}
