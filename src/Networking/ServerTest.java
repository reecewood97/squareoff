package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		bob.connect("127.0.0.1", 4444);

		server.startGame();
		
		wait(1000);
		
		server.reset();
		
		wait(1000);
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
















































