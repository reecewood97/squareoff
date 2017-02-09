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
	private MoveQueue q;
	private boolean inLobby, inGame;
	private String name;
	
	/**
	 * Constructor.
	 * @param server The PrintStream used to send messages to a server.
	 */
	public ClientSender(ObjectOutputStream server, MoveQueue q, String name) {
		this.server = server;
		this.q = q;
		this.name = name;
	}
	
	public void run() {
		inLobby = true;
		inGame = false;
		
		try {
			server.writeObject(name);
			server.flush();
		
			while(inGame) {
				Object obj = q.take(); 
				server.writeObject(obj);
				server.flush();
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
		inLobby = false;
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
}
