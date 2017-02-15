package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(5555);
		server.start();
		
		Client c1 = new Client("Bob");
		c1.connect("127.0.0.1", 5555);	
		
//		Client c2 = new Client("Jerry");
//		c2.connect("127.0.0.1", 4444);	
	}
}
















































