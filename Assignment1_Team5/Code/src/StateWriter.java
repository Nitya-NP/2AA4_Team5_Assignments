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

       // try-with-resources ensures the FileWriter is closed automatically
        try (FileWriter file = new FileWriter("../visualize/state.json")) {
            file.write(json);
        } catch (IOException e) {
            System.out.println("Failed to save state: " + e);
        }

    }

}

