package Networking;

import java.net.UnknownHostException;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(5555);
		server.start();
		
		Client c1 = new Client();
		try {
			c1.connect("127.0.0.1", 5555);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
	}
}
















































