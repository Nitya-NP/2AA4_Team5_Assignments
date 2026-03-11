import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the Game logic. It tests dice rolling, 
 * 
 * @author Raadhikka Gupta
 */

public class GameLogicTest {
    /**
     * Simple test player used because Player is abstract
     */
    class TestPlayer extends Player {
        public TestPlayer(int id) {
            super(id);
        }

        @Override
        public UserInput takeTurn() {
            return UserInput.ROLL;
        }
    }

    /**
     * To test that the Dice rolls are within the expected ranges.
     */
    @Test
    public void testDiceRollRange() {
        // Build dice
        MultiDice dice = new MultiDice();

        // Roll and verify the ranges
        for (int i = 0; i < 100; i++) {
            int roll = dice.roll();
            assertTrue(roll >= 2 && roll <= 12);
        }
    }

    /**
     * To test that the game is initialized correctly.
     */
    @Test 
    public void testGameInit() {
        // Create players
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new TestPlayer(i+1);
        }

        // Build and test game creation
        Game game = new Game(players, 10);
        assertNotNull(game);
    }

    /**
     * To test that minimum nummber of rounds work. 
     */
    @Test 
    public void testMinRounds() {
        // Create players 
        Player[] players = new Player[2];
        players[0] = new  TestPlayer(1);
        players[1] = new  TestPlayer(2);

        // Build game, start playing, and verify if it ran
        Game game = new Game(players, 1);
        game.start();
        assertTrue(true);
    }

    /**
     * To test that maximum nummber of rounds work. 
     */
    @Test 
    public void testMaxRounds() {
        // Create players 
        Player[] players = new Player[2];
        players[0] = new  TestPlayer(1);
        players[1] = new  TestPlayer(2);

        // Build game, start playing, and verify if it ran
        Game game = new Game(players, 8192);
        game.start();
        assertTrue(true);
    }

    /**
     * To test if the resource is given to a player
     */
    @Test 
    public void testResourceDistribution_notSeven() {
        GameLogger log = new GameLogger();
        Board board = new Board(log);
        Player player = new TestPlayer(1);
        Player[] players = { player };
        RobberActionsManager robber = new RobberActionsManager(board, players);
        board.setRobberManager(robber);

        // Compares if the old resource matches new one
        int prev = player.getTotalResources();
        board.produceResource(player, 8);
        int curr = player.getTotalResources();

        assertTrue(curr >= prev); 
    }

    /**
     * To test if the resource is not given to a player when 7 is rolled
     */
    @Test 
    public void testResourceDistribution_seven() {
        GameLogger log = new GameLogger();
        Board board = new Board(log);
        Player player = new TestPlayer(1);
        Player[] players = { player };
        RobberActionsManager robber = new RobberActionsManager(board, players);
        board.setRobberManager(robber);

        // Compares if the old resource matches new one
        int prev = player.getTotalResources();
        board.produceResource(player, 7);
        int curr = player.getTotalResources();

        assertEquals(prev, curr);
    }

    /**
     * To test that the game prints output and runs without crashing.
     */
    @Test
    public void testConsoleOutput() {
        Player[] players = new Player[2];
        players[0] = new TestPlayer(1);
        players[1] = new TestPlayer(2);

        // To verify output is created
        Game game = new Game(players, 1);
        game.start();
        assertNotNull(game);
    }
}