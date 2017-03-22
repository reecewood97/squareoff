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
	
	public mainMenuNetwork(String m) {
		s = new Server(m, port);
	}
	
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
	
	public ArrayList<String> getPlayers() {
		//Thread.sleep(1000);
		players = c.getPlayers();
		//System.out.println(players);
		return players;
		
	}
	
	public void setAIDifficulty(int i) {
		if (i==1)
			s.setAIDifficulty(Server.EASY_AI);
		else if (i==2)
			s.setAIDifficulty(Server.NORMAL_AI);
		else
			s.setAIDifficulty(Server.HARD_AI);
	}
	
	public void resetServer() {
		s.reset();
	}
	
	public void Disconnect() {
		c.disconnect();
	}
	
	public boolean isConnected() {
		return c.isConnected();
	}
	
	public boolean inGame() {
		return c.inGame();
	}
	
	public void runServer() {
		s.start();
	}
	
	public void startGame() {
		s.startGame();
	}

	public void closeServer() {
		s.close();
	}
	

}
