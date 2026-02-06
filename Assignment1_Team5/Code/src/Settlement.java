
/**
 * Represents a Settlement building in the game.
 * 
 * A Settlement is type of building that provides 1 victory point to its owner.
 */
public class Settlement extends Building {
	/**
	 * 
	 */
	private int points;

	/**
	 * 
	 * @return points - each settlement is worth 1 victory point
	 */
	@Override
	public int getPoints() {
		return points;
	}

	/**
	 * 
	 * @param owner - the player who owns the settlement
	 */
	public void Settlement(Player owner) {
		super(owner);
		this.points = 1;
	}
}
