import java.util.Scanner;
import java.util.regex.Pattern;

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
     * @param logger Gamelogger for output
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
            
            if (Pattern.matches("^(?i)roll$", input)) {
                hasRolled = true;
                return UserInput.ROLL;
            } else {
                logger.log(getPlayerId(), "Invalid input. You must roll first. Try again.");
                return takeTurn();
            }
        }

        logger.log(getPlayerId(), "Enter command:");
        String input = scanner.nextLine().trim();

        // Check GO command
        if (Pattern.matches("^(?i)go$", input)) {
            hasRolled = false;
            return UserInput.GO;
        }

        // Check LIST command
        if (Pattern.matches("^(?i)list$", input)) {
            return UserInput.LIST;
        }

        // Check BUILD SETTLEMENT command
        if (Pattern.matches("^(?i)build settlement \\d+$", input)) {
            return UserInput.BUILD_SETTLEMENT;
        }

        // Check BUILD CITY command
        if (Pattern.matches("^(?i)build city \\d+$", input)) {
            return UserInput.BUILD_CITY;
        }

        // Check BUILD ROAD command
        if (Pattern.matches("^(?i)build road \\d+,\\d+$", input)) {
            return UserInput.BUILD_ROAD;
        }

        logger.log(getPlayerId(), "Invalid command. Try again.");
        return takeTurn();

    }
}
