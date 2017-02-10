package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import gameLogic.Board;
import gameLogic.Move;

public class ServerReceiver extends Thread {
	
	private ObjectInputStream fromClient;
	private Board board;
	private boolean inGame;
	private ArrayList<String> players;
	
	public ServerReceiver(ObjectInputStream fromClient, Board board, ArrayList<String> players) {
		this.fromClient = fromClient;
		this.board = board;
		this.players = players; 
	}
	
	public void run() {
		inGame = true;
		
		try {
			String name = (String)fromClient.readObject();
			//TODO duplicate name
			if(players.size() < 4) {
				players.add(name);	
			}
			else {
				//TODO too many players.
			}
			
			
			while(!inGame) System.out.println(fromClient.readObject());
			
			Object stuff;
			while(!inGame && (stuff = fromClient.readObject()) != null) {
				if(stuff.equals(1)) {
					inGame = true;
				}
			}
			
			String input;
			while(inGame && (input = (String)fromClient.readObject()) != null) {				
				board.input(input);
			}
			fromClient.close();	
			
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() {
		inGame = true;
		inGame = false;
	}
}
