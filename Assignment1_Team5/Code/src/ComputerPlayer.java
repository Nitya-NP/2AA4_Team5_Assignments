import java.util.Random;

/**
 * Computer player respresents an automated player in Catan.
 * It randomly selects actions during its turn
 * 
 * The player must roll the dice before performing any other action. 
 * 
 * @author Nitya Patel
 */
public class ComputerPlayer extends Player {

    /**
     * Random object
     */
    private final Random rand = new Random();

    /**
     * Tracks whether the player has alrady rolled the dice this turn
     */
    private boolean hasRolled= false;

    /**
     * Constructs a ComputerPlayer with given player ID
     * @param playerId player id
     */
    public ComputerPlayer(int playerId) {
        super(playerId);

    }

    /**
     * Determines the next action for computer player.
     * The computer must roll the dice first before performing any other action
     * 
     */
    @Override
    public UserInput takeTurn() {

        if (!hasRolled) {
            hasRolled = true;
            return UserInput.ROLL;
        }

        UserInput[] actions = { 
            UserInput.BUILD_ROAD,
            UserInput.BUILD_SETTLEMENT,
            UserInput.BUILD_CITY,
            UserInput.LIST,
            UserInput.GO 
        };

        UserInput action= actions[rand.nextInt(actions.length)];
        if(action==UserInput.GO){
            hasRolled=false; //reset next turn
        }

        return action; 
    }

}
