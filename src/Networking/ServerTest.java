package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		//System.out.println(bob.connect("127.0.0.1", 4444));

		
		//server.startGame();
		
		//System.out.println(server.getPlayers());
		
	}	
}
















































