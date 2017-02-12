package UserInterface;

import javafx.beans.property.SimpleStringProperty;

public class Players {
    private SimpleStringProperty playerID;
    private SimpleStringProperty playerName;
 
    public Players(String pID, String pName) {
        this.playerID = new SimpleStringProperty(pID);
        this.playerName = new SimpleStringProperty(pName);
    }
 
    public String getPlayerID() {
        return playerID.get();
    }
    public void setPlayerID(String id) {
    	playerID.set(id);
    }
    
    public String getPlayerName() {
        return playerName.get();
    }
    public void setPlayerName(String name) {
    	playerName.set(name);
    }
}