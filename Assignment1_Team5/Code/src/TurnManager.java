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

    public TurnManager(Board board, GameLogger logger, Dice dice, RobberActionsManager robberManager) {
        this.board = board;
        this.logger = logger;
        this.dice = dice;
        this.robberManager = robberManager;
    }

    public void executeTurn(Player player) {
        currState = TurnState.START_TURN;
    
        while (currState != TurnState.END_TURN) {
            UserInput input = player.takeTurn();
            manageTurn(player, input);
        }
    }

    private void manageTurn(Player player, UserInput input) {
        switch(input) {
            case ROLL:
                handleRoll(player);
                break;
            case LIST:
                handleList(player);
                break;
            case GO:
                handleGo(player);
                break;
            case BUILD_SETTLEMENT:
                handleBuildSettlement(player);
                break;
            case BUILD_CITY:
                handleBuildCity(player);
                break;
            case BUILD_ROAD:
                handleBuildRoad(player);
                break;
            default:
                break;
        }
    }

    private void handleRoll(Player player) {
        if (currState != TurnState.START_TURN) {
            logger.log(player.getPlayerId(), "You already rolled.");
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
    }

    private void handleList(Player player) {
        logger.log(player.getPlayerId(), " Resources: " + player.listResources());
    }

    private void handleGo(Player player) {
        if (!isValidAction(player)) return;

        logger.log(player.getPlayerId(), "passes");
        currState = TurnState.END_TURN;
    }

    private void handleBuildSettlement(Player player) {
        if (!isValidAction(player)) return;

        board.buildSettlement(player);
    }

    private void handleBuildCity(Player player) {
        if (!isValidAction(player)) return;

        board.buildCity(player);
    }

    private void handleBuildRoad(Player player) {
        if (!isValidAction(player)) return;

        board.buildRoad(player);
    }

    private boolean isValidAction(Player player) {
        if (currState != TurnState.DO_ACTION) {
            logger.log(player.getPlayerId(), "You must roll first.");
            return false;
        }

        return true;
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
