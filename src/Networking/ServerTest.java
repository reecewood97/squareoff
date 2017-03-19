package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client bob = new Client("Bob");
		bob.connect("127.0.0.1", 4444);
		
		Client jerry = new Client("Jerry");
		jerry.connect("127.0.0.1", 4444);
		
		Client mary = new Client("Mary");
		mary.connect("127.0.0.1", 4444);
		
		Client sherly = new Client("Sherly");
		sherly.connect("127.0.0.1", 4444);

		server.startGame();
		
		wait(1000);
		
		server.reset();
		
		wait(1000);
		
		System.out.println(bob.isConnected());
		System.out.println(jerry.isConnected());
		System.out.println(mary.isConnected());
		System.out.println(sherly.isConnected());

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
















































