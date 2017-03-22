package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		bob.connect("127.0.0.1", 4444);
		
//		Client jerry = new Client("Jerry");
//		jerry.connect("127.0.0.1", 4444);

		server.startGame();
	}	
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
















































