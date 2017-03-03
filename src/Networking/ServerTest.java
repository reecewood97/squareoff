package Networking;

public class ServerTest {

	public static void main(String[] args) {
		Server server = new Server(4444);
		server.start();
		
		Client c1 = new Client("Bob");
		c1.connect("127.0.0.1", 4444);
		
		System.out.println(c1.getPlayers());
		
		Client c2 = new Client("Jerry");
		c2.connect("127.0.0.1", 4444);
		
		System.out.println(c2.getPlayers());
		
		Client c3 = new Client("Bob");
		c3.connect("127.0.0.1", 4444);
		
		System.out.println(c3.getPlayers());
		
		Client c4 = new Client("Bob");
		c4.connect("127.0.0.1", 4444);
		
		System.out.println(c4.getPlayers());
		
		Client c5 = new Client("Jerry");
		c5.connect("127.0.0.1", 4444);
		
		System.out.println(c5.getPlayers());
		
		System.out.println("hello");
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

//		server.startGame();
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		server.kick("Jerry");
//		
		//c1.disconnect();
	}
}
















































