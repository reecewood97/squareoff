package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		
		Client c1 = new Client("Bob");
		c1.connect("127.0.0.1", 4444);
		
		server.startGame();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c1.disconnect();
	}
}
















































