import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes the current game state to a JSON file for visualization
 * 
 * @author Nitya Patel
 */

public class StateWriter {
    
    public static void writeState(Board board, RobberActionsManager robberManager) {
        if (board == null) return; //Exit if board is null
        
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            
            // Robber
            Tile robberTile = robberManager.getCurrentTile();
            sb.append("  \"robberTile\": ").append(robberTile != null ? robberTile.getTileId() : -1).append(",\n");
            
            // Roads
            sb.append("  \"roads\": [\n");
            boolean first = true;
            for (Road road : board.getRoad()) {
                if (road != null) {
                    if (!first)  sb.append(",\n");
                    Node[] nodes = road.getConnectedNodes();
                    sb.append("    { \"a\": ").append(nodes[0].getNodeId()).append(", \"b\": ").append(nodes[1].getNodeId()) .append(", \"owner\": ").append(road.getOwner().getPlayerId()).append(" }");
                    first = false;
                }
            }
            sb.append("\n  ],\n");
            
            // Buildings
            sb.append("  \"buildings\": [\n");
            first = true;
            for (Node node : board.getNode()) {
                if (node.isOccupied()) {
                    if (!first)  sb.append(",\n");
                    
                    Building b = node.getBuilding();
                    String type;
                    
                    if (b instanceof City) {
                        type = "city";
                    } else {
                        type = "settlement";
                    }
                    
                    sb.append("    { \"node\": ").append(node.getNodeId()).append(", \"owner\": ").append(b.getOwner().getPlayerId()).append(", \"type\": \"").append(type).append("\" }");
                    
                    first = false;
                }
            }
            sb.append("\n  ]\n");
            
            sb.append("}");
            
            FileWriter file = new FileWriter("../visualize/state.json");
            file.write( sb.toString());
            file.close();
            
        } catch (IOException e) {
            System.out.println("Failed to save state: " + e.getMessage());
        }
    }
}