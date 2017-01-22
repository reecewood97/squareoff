package Networking;

import java.io.PrintStream;
import java.util.ArrayList;

import GameLogic.UserInput;

public class ClientSender extends Thread {

	private PrintStream server;
	private UserInput input;
	private boolean running;
	
	public ClientSender(PrintStream server) {
		this.server = server;
		input = new UserInput();
	}
	
	public void run() {
		running = true;
		
		try {
			while(running) {
				ArrayList<String> nextInputs = input.getInputStrings();
				for(String s: nextInputs) {
					server.print(s);	
				}
				server.println();
				
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
