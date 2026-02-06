
/**
 * Represents a City building in the game.
 * 
 * A City is type of building that provides 2 victory points to its owner.
 */
public class Cities extends Building {
	/**
	 * 
	 */
	private int points;

	/**
	 * 
	 * @return points - each city is worth 2 victory points
	 */
	@Override
	public int getPoints() {
		return points;
	}

	/**
	 * 
	 * @param owner - the player which owns the city
	 */
	public void Cities(Player owner) {
		super(owner);
		points = 2;
	}
}
