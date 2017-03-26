/**
 * The mainMenuNetwork class is where all the networking call of the game are made
 * For eg: Pressing the start game button in mainMenu runs a method in this class to start the game for all clients
 * @author ksk523
 */

package UserInterface;

import java.util.ArrayList;
import Networking.Client;
import Networking.Server;

public class mainMenuNetwork {
	
	ArrayList<String> players = new ArrayList<>();
	int port = 4444;
	Server s;
	Client c;
	
    /**
     * New instance of a mainMenuNetwork - it creates a new Server
     * @param m Name of map to be created in the server
     */
	public mainMenuNetwork(String m) {
		s = new Server(m, port);
	}
	
	/**
	 * This method runs the connect method in the Client
	 * It tries to connect a client to the Server
	 * @return an int of the result of the connection attempt
	 * 0 for the client successfully connecting to the given Server address
	 * 1 for a failed connection attempt (there isn't a Server with the given address)
	 * 2 for if the client (or a client with the same name) is already connected to the Server
	 */
	public int connectToHost(String hostName, String name) {
		c = new Client(name);
		int val = (c.connect(hostName, port));
		if ( val == 0 ) {
			players = c.getPlayers();
			return 0;
		}
		else
			return val;
	}
	
	/**
	 * This method runs the getPlayers method in the Client
	 * It gets a list of the players connected to the server
	 * @return a String ArrayList of the players connected to the server
	 */
	public ArrayList<String> getPlayers() {
		players = c.getPlayers();
		return players;
		
	}
	
	/**
	 * This method runs the setAIDifficulty method in the Server
	 * It sets how difficult the AI will be for the next game
	 */
	public void setAIDifficulty(int i) {
		if (i==1)
			s.setAIDifficulty(Server.EASY_AI);
		else if (i==2)
			s.setAIDifficulty(Server.NORMAL_AI);
		else
			s.setAIDifficulty(Server.HARD_AI);
	}
	
	/**
	 * This method runs the reset method in the Server
	 * It resets the server
	 */
	public void resetServer() {
		s.reset();
	}
	
	/**
	 * This method runs the disconnect method in the Client
	 * It disconnects the client from the Server
	 */
	public void Disconnect() {
		c.disconnect();
	}
	
	/**
	 * This method runs the isConnected method in the Client
	 * It checks whether the client is connected to the Server or not
	 * @return a boolean of whether the client is connected to the Server or not
	 */
	public boolean isConnected() {
		return c.isConnected();
	}
	
	/**
	 * This method runs the inGame method in the Client
	 * It checks whether the client is in a game or not
	 * @return a boolean of whether the client is in the game or not
	 */
	public boolean inGame() {
		return c.inGame();
	}
	
	/**
	 * This method starts the Server thread
	 * It starts the Server
	 */
	public void runServer() {
		s.start();
	}
	
	/**
	 * This method runs the startGame method in the Server
	 * It starts the game
	 */
	public void startGame() {
		s.startGame();
	}

	/**
	 * This method runs the close method in the Server
	 * It closes the server
	 */
	public void closeServer() {
		s.close();
	}
	

}
