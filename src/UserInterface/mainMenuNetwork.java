package UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import Networking.Client;
import Networking.Server;

public class mainMenuNetwork {
	
	ArrayList<String> players = new ArrayList<>();
	Server s = new Server(123);
	Client c;
	
	public boolean connectToHost(String hostName, String name) {
		
		c = new Client(name);
		
		String[] address;
		
		try {
			address = hostName.split(":");
			
			if ((address[0].length()<7) | Integer.parseInt(address[1])!=123) {
			//if (!(address[0].equals("127.0.0.1")) | Integer.parseInt(address[1])!=123) {
				//System.out.println("Address too small or incorrect port");
				System.out.println("Address or incorrect port");
				return false;
			}
			
			System.out.println(address[0]);
			System.out.println(Integer.parseInt(address[1]));
			
			if (c.connect(address[0], Integer.parseInt(address[1]))) {
				players = c.getPlayers();
				for (int i=0; i<players.size(); i++) {
					System.out.println(players.get(i));
				}
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
		players.add("Player 1 Name Here - should be the host");
		players.add("Player 2 Name Here - should be a human player who's 1st to join");
		players.add("Player 3 Name Here - could be ai or next human to join");
		players.add("Player 4 Name Here - could be ai or last human to join");
		for (int i=0; i<players.size(); i++) {
			System.out.println(players.get(i));
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
