package Networking;

public class ServerTest {

	public static void main(String[] args) {
//		Server server = new Server(4444);
//		server.start();
//		
//		Client bob = new Client("Bob");
//		bob.connect("127.0.0.1", 4444);
//		
//		Client jerry = new Client("Jerry");
//		jerry.connect("127.0.0.1", 4444);
//
//		server.startGame();
		
		String s = "hi";
		String t = "hi";
		
		
		waitForNotEqual(s, t);
		System.out.println("hello");
	}	
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void waitForNotEqual(Object ob1, Object ob2) {
		while(ob1.equals(ob2)) {
			try {			
				Thread.sleep(50);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
















































