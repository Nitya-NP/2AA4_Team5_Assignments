/**
 * This class controls the actions that occurs during a player's turn in the game.
 * 
 * @author Ranica Chawla, Raadhikka Gupta
 */

import java.util.Random;

public class TurnManager {
    private TurnState currState;

    private Board board;
    private GameLogger logger;
    private Dice dice;

    /**
     * To manage the robber actions when activated
     */
    private RobberActionsManager robberManager;

    /**
	 * Random number generator.
	 */
	private final Random rand = new Random();

    public TurnManager(Board board, GameLogger logger, Dice dice, RobberActionsManager robberManager) {
        this.board = board;
        this.logger = logger;
        this.dice = dice;
        this.robberManager = robberManager;
    }

    public void executeTurn(Player player) {
        currState = TurnState.START_TURN;
    
        while (currState != TurnState.END_TURN) {
            // we want takeTurn to essentially get the users input (either go, build_city, etc.) <- it should return one of these enums
            // u could also rename it maybe to match its functionality better
            //UserInput input = player.takeTurn();
            //manageTurn(player, input);
        }
    }

    private void manageTurn(Player player, UserInput input) {
        switch(input) {
            case ROLL:
                if (currState != TurnState.START_TURN) {
                    System.out.println("You already rolled.");
                    return;
                }

                int diceValue = roll(player);

                // when current state is robber
                if (currState == TurnState.ROBBER) {
                    // to discard half the cards
                    robberManager.discardResourceCards();

                    // to move the robber
                    robberManager.moveRobber(board);

                    // to steal resource
                    Player victim = robberManager.chooseVictim(player);
                    if (victim != null) {
                        robberManager.stealResource(victim);
                    }
                }
                
                // resources can be produced
                else {
                    board.produceResource(player, diceValue);
                }

                break;
            case LIST:
                player.listResources();
                break;
            case GO:
                if (currState == TurnState.DO_ACTION) {
                    logger.log(player.getPlayerId(), "passes");
                    currState = TurnState.END_TURN;
                } else {
                    System.out.println("You must roll first.");
                }
                break;
            case BUILD_SETTLEMENT:
                if (currState == TurnState.DO_ACTION) {
                    board.buildSettlement(player);
                } else {
                    System.out.println("You must roll first.");
                }
                break;
            case BUILD_CITY:
                if (currState == TurnState.DO_ACTION) {
                    board.buildCity(player);
                } else {
                    System.out.println("You must roll first.");
                }
                break;
            case BUILD_ROAD:
                if (currState == TurnState.DO_ACTION) {
                    board.buildRoad(player);
                } else {
                    System.out.println("You must roll first.");
                }
                break;
            
        }
    }

    private int roll(Player player) {
        int diceValue = dice.roll();
        logger.log(player.getPlayerId(), "rolled " + diceValue);

        if (diceValue == 7) {
            currState = TurnState.ROBBER;
        } else {
            currState = TurnState.DO_ACTION;
        }

        return diceValue;
    }
}
