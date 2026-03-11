import java.io.FileWriter;
import java.io.IOException;

/**
 * The StateWriter class is responsible for creating a JSON representation
 * of the current game state and saving it to a file. This JSON can then
 * be used by the visualizer to display the board.
 */
public class StateWriter {


    public static void writeState() {

        // JSON string representing the game state
        // Contains "roads" and "buildings" arrays with sample data
        String json =
        "{\n" +
        "  \"roads\": [\n" +
        "    { \"a\": 11, \"b\": 12, \"owner\": \"RED\" }\n" +
        "  ],\n" +
        "  \"buildings\": [\n" +
        "    { \"node\": 12, \"owner\": \"RED\", \"type\": \"SETTLEMENT\" }\n" +
        "  ]\n" +
        "}";

        try {

            FileWriter file = new FileWriter("Assignment1_Team5/Code/visualize/state.json");

            // Write the JSON string to the file
            file.write(json);

            // Close the file to ensure data is saved properly
            file.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}