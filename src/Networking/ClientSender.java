package Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;

import GameLogic.Move;


/**
 * The client sender class. This relays any string s generated by the client to a server it's connected to.
 * @author djs568
 *
 */
public class ClientSender extends Thread {

	private ObjectOutputStream server;
	private Queue q;
	private boolean running;
	private String name;
	
	/**
	 * Constructor.
	 * @param server The PrintStream used to send messages to a server.
	 */
	public ClientSender(ObjectOutputStream server, Queue q, String name) {
		this.server = server;
		this.q = q;
		this.name = name;
	}
	
	public void run() {
		running = true;
		
		try {
			server.writeObject(name);
			server.flush();
			server.reset();
		
			while(running) {
				Object obj = q.take(); 
				server.writeObject(obj);
				server.flush();
				server.reset();
				
				sleep(40);
			}
			
			server.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() {
		running = false;
	}
	
	public void send(int msg) {
		try {
			server.writeObject(msg);
			server.flush();
			server.reset();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
