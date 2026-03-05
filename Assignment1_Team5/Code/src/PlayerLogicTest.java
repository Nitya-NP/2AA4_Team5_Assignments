import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Player logic test cases 
 * 1. Turn switching
 * 2. Partition: Produce correct resource
 * 3. Validation: Correct points after turn
 * 4. Validation: Correct resources after building
 * 5. Validation: Player builds if >=7 resources
 * 
 * @author Nitya Patel 
 */
public class PlayerLogicTest {


	/**
	 * Test switching turns doesnt break the game.
	 */
	@Test
	public void testPlayerSwitchingTurns() {
		Player[] players= new Player[2];
		players[0] =new Player(1);
		players[1]=new Player(2);

		Game game= new Game(players,1);

		int p1Points= players[0].getPoints();
		int p2Points= players[1].getPoints();

		int p1Resource= players[0].getTotalResources();
		int p2Resource= players[1].getTotalResources();

		game.start();

		//After round, points/ resources should have updates
		assertTrue(players[0].getPoints()>=p1Points); //Player 1 points should not descrease
		assertTrue(players[1].getPoints()>=p2Points); //Player 2 points should not descrease

		assertTrue(players[0].getPoints()>=p1Resource); //Player 1 resource should not descrease
		assertTrue(players[1].getPoints()>=p2Resource); //Player 2 resource should not descrease");
		
	}

	/**
	 * Test Resouce production when dice roll is not seven
	 */
	@Test
	public void testProduceResourceDiceNotSeven(){
		GameLogger l= new GameLogger();
		Board b= new Board(l);

		Player player= new Player(1);

		int before=player.getTotalResources();
		b.takeTurn(player, 5); //not 7
		int after= player.getTotalResources();
		assertTrue(after>=before);
	}

	/**
	 * Test Resouce production when dice roll is seven
	 */
	@Test
	public void testProduceResourceDiceSeven(){
		GameLogger l= new GameLogger();
		Board b= new Board(l);

		Player player= new Player(1);

		int before=player.getTotalResources();
		b.takeTurn(player, 7); //7
		int after= player.getTotalResources();
		assertTrue(after>=before);
	}


	/**
	 * Test that adding a settlement increases player's points by 1
	 */
	@Test
	public void testSettlementAddsOnePoint(){
		Player player=new Player(1);
		Building s=new Settlement(player);
		player.addBuilding(s);

		assertEquals(1, player.getPoints());
		
	}

	/**
	 * Test that adding a cities increases player's points by 2
	 */
	@Test
	public void testCityAddsTwoPoint(){
		Player player=new Player(1);
		Building c=new Cities(player);
		player.addBuilding(c);

		assertEquals(2, player.getPoints());
		
	}

	/**
	 * Test that adding a road does NOT give points
	 */
	@Test
	public void testRoadAddsZeroPoint(){
		Player player=new Player(1);
		Node n1= new Node(1);
		Node n2= new Node(2);

		Roads road= new Roads(new Node[]{n1,n2}, player);
		player.addBuilding(road);

		assertEquals(0,player.getPoints());

	}

	/**
	 * Test that points after taking a turn are at least as much as before
	 */
	@Test
	public void testPointsAfterBuilding() {
		GameLogger logger = new GameLogger();
		Board board = new Board(logger);

		Player player = new Player(1);

		int before = player.getPoints();

		board.takeTurn(player, 6);

		int after = player.getPoints();

		assertTrue(after >= before);
	}
 





}
