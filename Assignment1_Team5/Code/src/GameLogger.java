/**
 * The GameLogger class is responsible for logging the actions of players during the game. 
 * It keeps track of the number of rounds, and provides a method to log player actions in required format.
 * @author Ranica Chawla
 */
public class GameLogger {
    // Private attributes to keep track of the number of rounds
    private int rounds;

    /**
     * Constructor for the GameLogger class that sets the rounds to 1.
     */
    public GameLogger() {
        this.rounds = 1;
    }

    /**
     * Private method to format the beginning of the formatted output with the current round number and player ID.
     * @param playerId the ID of the player whose doing an action
     * @return a string in the format of "[rounds] / playerId: "
     */
    private String start(int playerId) {
        return "[" + rounds + "] / " + playerId + ": ";
    }

    /**
     * Log the current action of a player in the required format
     * @param playerId the ID of the player whose doing an action
     * @param action the action that the player is doing
     */
    public void log(int playerId, String action) {
        System.out.println(start(playerId) + action);
    }

    /**
     * Method to let the logger know that the current turn ended so it may increment the rounds counter for the next turn.
     */
    public void endTurn() {
        rounds++;
    }
}
