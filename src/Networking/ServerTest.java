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
















































