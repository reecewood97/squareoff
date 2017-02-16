package Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;

import ai.AI;

/**
 * 
 *
 */
public class AIClientSender extends Thread {

	private ObjectOutputStream server;
	private Queue q;
	private boolean inGame;
	private String name;
	private AI ai;
	
	/**
	 * Constructor.
	 * @param ai 
	 * @param server The PrintStream used to send messages to a server.
	 */
	public AIClientSender(AI ai, ObjectOutputStream server, Queue q, String name) {
		this.server = server;
		this.q = q;
		this.ai = ai;
		this.name = name;
	}
	
	public void run() {
		inGame = true;
		
		try {
			server.writeObject(name);
			server.flush();
			
			while(!inGame) {
				//server.writeObject("");
				//server.flush();
			}
		
			while(inGame) {
				ai.determineState();
				Object obj = q.take(); 
				server.writeObject(obj);
				server.flush();
				
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
		inGame = false;
	}
	
	public void play() {
		try {
			server.writeObject(1);
			server.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void send(Object obj) {
		try {
			server.writeObject(obj);
			server.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
