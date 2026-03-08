/**
 * To implement the robber's actions of discarding, stealing, moving, and blocking resources to be built when provoked.
 * 
 * @author Raadhikka Gupta
 */

import java.util.Random;

public class RobberActionsManager {
	/**
	 * To store the robber's current location.
	 */
	private Tile currentTile;

	/**
	 * Random number generator.
	 */
	private final Random rand = new Random();

	/**
	 * All stealable resources
	 */
	private static final Resources[] STEAL_RESOURCES = {Resources.BRICK, Resources.GRAIN, Resources.LUMBER, Resources.ORE, Resources.WOOL};

	/**
	 * To initialize the robber to be on the desert tile initially
	 */
	public RobberActionsManager(Tile desertTile) {
		this.currentTile = desertTile;
	}

	/**
	 * Handles to discard resource cards when a 7 is rolled.
	 * 
	 * @return True if discarded
	 */
	public boolean discardResourceCards(Player[] players) {
		// to validate
		if (players == null)
			return false;

		// to loop through the players
		for (Player p : players) {
			// to validate
			if (p == null) 
				continue;

			int totalResources = p.getTotalResources();

			// if the current player has more than 7 cards
			if (totalResources > 7) {
				int discardCardAmount = totalResources / 2;

				// to remove half the resources the player has, randomly
				for (int i = 0; i < discardCardAmount; i++) {
					// to find a resource that's available and then remove it
					Resources toRemove;
					do { 
						toRemove = STEAL_RESOURCES[rand.nextInt(STEAL_RESOURCES.length)];
					} while (!p.hasResources(toRemove, 1));

					p.removeResource(toRemove, 1);
				}
			}
		}

		return true;
	}

	/**
	 * To move the robber to a new tile
	 * 
	 * @param newTile the new tile to move to
	 * @return True when moved
	 */
	public boolean moveRobber(Tile newTile) {
		// valid tile
		if (newTile == null)
			return false;
		
		// if it's the same tile
		if (newTile == currentTile)
			return false;
		
		currentTile = newTile;
		return true;
	}

	/**
	 * Returns the current tile that the robber is on
	 * 
	 * @return Tile robber is on
	 */
	public Tile getCurrentTile() {
		return currentTile;
	}

	/**
	 * To check if the robber is on the tile given
	 * 
	 * @param tile the tile to check with
	 * @return true if it is
	 */
	public boolean isRobberOnTile(Tile tile) {
		if (tile == null)
			return false;
		
		return currentTile == tile;
	}

	/**
	 * To steal a resource from a player
	 * 
	 * @param currentPlayer the player to steal from
	 */
	public void stealResource(Player currentPlayer) {
		// if they don't have resources
		if (currentPlayer == null || currentPlayer.getTotalResources() == 0)
			return;

		// to find a resource that's available and then remove it
		Resources toRemove;
		do { 
			toRemove = STEAL_RESOURCES[rand.nextInt(STEAL_RESOURCES.length)];
		} while (!currentPlayer.hasResources(toRemove, 1));

		currentPlayer.removeResource(toRemove, 1);
	}
}
