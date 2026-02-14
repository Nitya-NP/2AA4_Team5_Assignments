import java.util.Scanner;

/**
 * Main method 
 * @author Nitya Patel
 */
public class Demonstrator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rounds =0; 

        // Keep asking the user until a valid number of rounds is entered
        while (true) {
            System.out.print("Enter number of rounds to simulate (1-8192): ");
            if (scanner.hasNextInt()) { // Check if input is an integer
                rounds = scanner.nextInt();
                if (rounds >= 1 && rounds <= 8192) {
                    break;
                } else {
                    System.out.println("Invalid number of rounds. Must be between 1 and 8192.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); 
            }
        }

        // Create 4 players
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i + 1);
        }

        // Create game with players and rounds
        Game game = new Game(players, rounds);

        // Start the game
        game.start();

        // Print final points
        System.out.println("\n--- Game Over ---");
        for (Player p : players) {
            System.out.println("Player " + p.getPlayerId() + " final points: " + p.getPoints());
        }

        scanner.close();
    }
}
