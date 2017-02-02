package Networking;

import java.net.*;
import java.io.*;

public class Client {
	
	private Socket socket;
	private PrintStream toServer;
	private BufferedReader fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	
	public Client(String host, int port) {
		socket = null;
		toServer = null;
		fromServer = null;
	}
	
	public void connect(String ip, int port) throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		toServer = new PrintStream(socket.getOutputStream());
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		sender = new ClientSender(toServer);
		receiver = new ClientReceiver(fromServer);
		
		sender.start();
	    receiver.start();
	}
	
	public void disconnect() throws IOException {
		socket.close();
		sender.close();
		receiver.close();
	}
}
