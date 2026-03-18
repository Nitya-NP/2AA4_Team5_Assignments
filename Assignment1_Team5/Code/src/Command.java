/**
 * The interface is an action that can be executed, undo, and redo in the system. It helps
 * with 
 * 
 * @author Raadhikka Gupta, Ranica Chawla
 */
public interface Command {
    public void execute();
    public void undo();
    public void redo();
}