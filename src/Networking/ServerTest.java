package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		bob.connect("127.0.0.1", 4444);
		
		//System.out.println(server.kick("Bob"));
		
		System.out.println(bob.resetConnection());
		
		server.startGame();
		
//		server.close();
//		System.out.println(bob.isConnected());
	}	
}
















































