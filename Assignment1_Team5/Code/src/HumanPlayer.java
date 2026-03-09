import java.util.Scanner;

/**
 * Human player represents a real person playing Catan via console input.
 * The player must roll first, then can build, list cards, or end turn with Go.
 * 
 * @author Krisha Patel
 */
public class HumanPlayer extends Player {

    private Scanner scanner;
    private GameLogger logger;


    //Tracks whether the player has already rolled the dice this turn
    private boolean hasRolled = false;

    /**
     * Constructs a HumanPlayer with given player ID
     * @param playerId player id
     */
    public HumanPlayer(int playerId, GameLogger logger) {
        super(playerId);
        this.scanner = new Scanner(System.in);
        this.logger = logger;
    }

    /**
     * Gets the next action from human player
     * The player must roll first before performing any other action.
     * 
     * @return UserInput enum representing the player's chosen action
     */
    @Override
    public UserInput takeTurn() {

        if (!hasRolled) {
            logger.log(getPlayerId(), "Human: Enter 'roll':");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("roll")) {
                hasRolled = true;
                return UserInput.ROLL;
            } else {
                logger.log(getPlayerId(), "Invalid input. You must roll first. Try again.");
                return takeTurn();
            }
        }

        logger.log(getPlayerId(), "Enter command:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("go")) {
            hasRolled = false;
            return UserInput.GO;
        }

        if (input.equalsIgnoreCase("list")) {
            return UserInput.LIST;
        }

        if (input.toLowerCase().startsWith("build settlement")) {
            return UserInput.BUILD_SETTLEMENT;
        }

        if (input.toLowerCase().startsWith("build city")) {
            return UserInput.BUILD_CITY;
        }

        if (input.toLowerCase().startsWith("build road")) {
            return UserInput.BUILD_ROAD;
        }

        logger.log(getPlayerId(), "Invalid command. Try again.");
        return takeTurn();
    }
}
