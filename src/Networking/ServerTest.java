package Networking;

public class ServerTest {
	
	public static String[] players;
	public static int numberOfPlayers;

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		
		for(int i = 0; i < 50; i++) {
			bob.connect("127.0.0.1", 4444);
			System.out.println(bob.getPlayers());
			bob.disconnect();
		}
		
		//server.startGame();
	}	

}
















































