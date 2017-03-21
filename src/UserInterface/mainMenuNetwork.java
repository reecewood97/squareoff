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
	Server s = new Server(port);
	Client c;
	
	public boolean connectToHost(String hostName, String name) {
		
		c = new Client(name);
		//String[] address;
		
		try {
			/*
			address = hostName.split(":");
			if ((address[0].length()<7) | Integer.parseInt(address[1])!=port) {
				//System.out.println("Incorrect address/port or address too small");
				return false;
			}
			if (c.connect(address[0], Integer.parseInt(address[1]))) {
				//Thread.sleep(1000);
				players = c.getPlayers();
				return true;
			}
			*/
			if (c.connect(hostName, port)) {
				players = c.getPlayers();
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			//System.out.println("Exception caught in mainMenuNetwork");
			//e.printStackTrace();
			return false;
		}
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
