/**
 * Class used when creating the lobby table in javafx
 * @author ksk523
 */
package UserInterface;

import javafx.beans.property.SimpleStringProperty;

public class Players {
    private SimpleStringProperty playerID;
    private SimpleStringProperty playerName;
 
    /**
     * New instance of a player
     * @param pID player id
     * @param pName player name
     */
    public Players(String pID, String pName) {
        this.playerID = new SimpleStringProperty(pID);
        this.playerName = new SimpleStringProperty(pName);
    }
 
    /**
     * Gets the player id
     * @return player id as string
     */
    public String getPlayerID() {
        return playerID.get();
    }
    
    /**
     * Sets the player id
     * @param id new id of player
     */
    public void setPlayerID(String id) {
    	playerID.set(id);
    }
    
    /**
     * Gets the player name
     * @return player name as string
     */
    public String getPlayerName() {
        return playerName.get();
    }
    
    /**
     * Sets the player name
     * @param name new name of player
     */
    public void setPlayerName(String name) {
    	playerName.set(name);
    }
}