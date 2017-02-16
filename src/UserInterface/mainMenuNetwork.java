/**
 * The mainMenuNetwork class is where all the networking call of the game are made
 * For eg: Pressing the start game button in mainMenu runs a method in this class to start the game for all clients
 * @author ksk523
 */

package UserInterface;

import java.io.IOException;
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
		
		String[] address;
		
		try {
			address = hostName.split(":");
			
			if ((address[0].length()<7) | Integer.parseInt(address[1])!=port) {
				System.out.println("Incorrect address/port or address too small");
				return false;
			}
			
			if (c.connect(address[0], Integer.parseInt(address[1]))) {
				getPlayers();
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			System.out.println("Exception caught in mainMenuNetwork");
			//e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<String> getPlayers() {
		try {
			Thread.sleep(1000);
			players = c.getPlayers();
			//System.out.println(players);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return players;
		
	}
	
	public void runServer() {
		System.out.println("Starting the Server - mainMenuNetwork");
		s.start();
	}
	
	public void startGame() {
		c.play();
	}

	public void closeServer() {
		System.out.println("Closing the Server - mainMenuNetwork");
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
