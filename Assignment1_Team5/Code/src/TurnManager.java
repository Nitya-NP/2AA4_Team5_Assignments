
public class TurnManager {
    private TurnState currState;

    private Board board;
    private GameLogger logger;
    private Dice dice;

    public TurnManager(Board board, GameLogger logger, Dice dice) {
        this.board = board;
        this.logger = logger;
        this.dice = dice;
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

                if (currState == TurnState.ROBBER) {
                    // do robber stuff here
                }
                
                board.produceResource(player, diceValue);
                break;
            case LIST:
                // to implement
                //player.listResources();
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
