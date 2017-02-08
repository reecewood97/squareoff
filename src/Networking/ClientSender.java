package Networking;

import java.io.PrintStream;
import java.util.ArrayList;

import GameLogic.UserInput;

/**
 * The client sender class. This relays any string s generated by the client to a server it's connected to.
 * @author djs568
 *
 */
public class ClientSender extends Thread {

	private PrintStream server;
	private UserInput input;
	private boolean running;
	
	/**
	 * Constructor.
	 * @param server The PrintStream used to send messages to a server.
	 */
	public ClientSender(PrintStream server,UserInput q) {
		this.server = server;
		input = new UserInput();
		this.input = q;
	}
	
	/**
	 * Run method of the thread. Reads an ArrayList of strings from from the client 25 times a second.
	 */
	public void run() {
		running = true;
		
		try {
			while(running) {
				String nextInput = input.getInputStrings(); // I'm pretty sure we're safer having a q here so we don't get odd  null points.
				//for(String s: nextInputs) {
				//	server.print(s);	
				//}
				server.println(nextInput);
				server.flush();
				
				sleep(40);
			}
			
			server.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void close() {
		running = false;
	}
}
