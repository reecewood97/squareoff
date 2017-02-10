package userInterface;

import java.io.IOException;

import networking.Client;
import networking.Server;

public class mainMenuNetwork {
	
	public boolean connectToHost(String hostName, String name) {
		
		Client c = new Client(name);
		
		String[] address;
		
		try {
			address = hostName.split(":");
			if (c.connect(address[0], Integer.parseInt(address[1])))
				return true;
		} catch (Exception e) {
			System.out.println("Exception caught in mainMenuNetwork");
			//e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void runServer(Server s) {
		System.out.println("Starting the Server - mainMenuNetwork");
		s.start();
	}
	

	public void closeServer(Server s) {
		System.out.println("Closing the Server - mainMenuNetwork");
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
