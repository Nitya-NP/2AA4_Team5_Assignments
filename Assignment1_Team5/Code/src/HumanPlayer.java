import java.util.Scanner;

/**
 * Human player represents a real person playing Catan via console input.
 * The player must roll first, then can build, list cards, or end turn with Go.
 * 
 * @author Krisha Patel
 */
public class HumanPlayer extends Player {

    private final Scanner scanner;

    //Tracks whether the player has already rolled the dice this turn
    private boolean hasRolled = false;

    /**
     * Constructs a HumanPlayer with given player ID
     * @param playerId player id
     */
    public HumanPlayer(int playerId) {
        super(playerId);
        this.scanner = new Scanner(System.in);
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
            System.out.println("Player " + getPlayerId() + " (Human): Enter 'roll':");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("roll")) {
                hasRolled = true;
                return UserInput.ROLL;
            } else {
                System.out.println("Invalid input. You must roll first. Try again.");
                return takeTurn();
            }
        }

        System.out.println("Enter command:");
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

        System.out.println("Invalid command. Try again.");
        return takeTurn();
    }
}
