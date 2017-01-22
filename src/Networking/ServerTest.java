package Networking;

public class ServerTest {

	public static void main(String[] args) {

		Server server = new Server(4444);
		server.start();
		Client client1 = new Client("Jerry", 4444);
		client1.start();

	}

}
