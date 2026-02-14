import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main method 
 * This class reads file "turns.txt" to get number of turns (rounds)
 * 
 * It creates 4 players, runs the game, and prints the final points
 * @author Nitya Patel
 */
public class Demonstrator {
    public static void main(String[] args) {
        int rounds = 0;

        //Reads the file
        try (BufferedReader br = new BufferedReader(new FileReader("turns.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("turns:")) {
                    String[] parts = line.split(":");
                    rounds = Integer.parseInt(parts[1].trim());
                    break;
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
            return;
        }

        // Validate rounds
        if (rounds < 1 || rounds > 8192) {
            System.out.println("Invalid number of rounds in config file. Must be between 1 and 8192.");
            return;
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
    }
}
 


