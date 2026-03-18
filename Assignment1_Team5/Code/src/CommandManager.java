import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage commands 
 * 
 * @author Raadhikka Gupta, Ranica Chawla
 */

public class CommandManager {
    private List<Command> commandBuffer; // list to store commands
    private int currentCommand; // currentCommand command integer

    public CommandManager() {
        this.commandBuffer = new ArrayList<Command>();
        this.currentCommand = -1;
    }

    public void executeCommand(Command command) {
        // Remove redoable commands
        while (commandBuffer.size() > currentCommand + 1) {
            commandBuffer.remove(commandBuffer.size() - 1);
        }

        command.execute();
        commandBuffer.add(command);
        currentCommand++;
    }

    public void undo() {
        if (currentCommand >= 0) {
            commandBuffer.get(currentCommand).undo();
            currentCommand--;
        }
    }

    public void redo() {
        if (currentCommand < commandBuffer.size() - 1) {
            currentCommand++;
            commandBuffer.get(currentCommand).redo();
        }
    }
}