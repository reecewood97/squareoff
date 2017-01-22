package Networking;

import java.net.*;
import java.io.*;

public class Client extends Thread {
	
	private String host;
	private int port;
	private Socket socket;
	private PrintStream toServer;
	private BufferedReader fromServer;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void run() {
		socket = null;
		toServer = null;
		fromServer = null;
		
		try {
			socket = new Socket(host, port);
		    toServer = new PrintStream(socket.getOutputStream());
		    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
			//TODO
		}
		catch(IOException e) {
			e.printStackTrace();
			//TODO
		}
		
		ClientSender sender = new ClientSender(toServer);
		ClientReceiver receiver = new ClientReceiver(fromServer);
		
		sender.start();
	    receiver.start();
		
		try {
	        sender.join();
	        toServer.close();
	        receiver.join();
	        fromServer.close();
	        socket.close();
	      }
	      catch (IOException e) {
	    	  e.printStackTrace();
	    	  //TODO
	      }
	      catch (InterruptedException e) {
	    	  e.printStackTrace();
	    	  //TODO
	      }
	}
}
