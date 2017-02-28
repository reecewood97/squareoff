package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.Move;

public class ServerReceiver extends Thread {
	
	private ObjectInputStream fromClient;
	private Board board;
	private boolean running, inGame;
	private ArrayList<String> players;
	private ClientTable table;
	private String name;
	
	public ServerReceiver(ObjectInputStream fromClient, Board board, ArrayList<String> players, ClientTable table) {
		this.fromClient = fromClient;
		this.board = board;
		this.players = players; 
		this.table = table;
		name = "";
	}
	
	public void run() {
		running = true;
		inGame = false;
		
		try {
			name = (String)fromClient.readObject();
			if(players.contains(name)) {
				//TODO 
			}
			else if(players.size() < 4){ 
				players.add(name);
				board.addName(name);
			}
			else {
				//TODO 
			}
			
			Object ob;
			while(running && !inGame && (ob = fromClient.readObject()) != null) { 
				if(ob.getClass().isInstance(0) && (int)ob == Server.QUIT) 
					quit();
			}
			
			
			Object input;
			while(running && inGame && (input = fromClient.readObject()) != null) {
				if(input.getClass().isInstance("A String"))
					board.input((String)input);
				else if((int)input == Server.QUIT) 
					quit();
			}
		}
		catch(IOException e) {
			close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void startGame() {
		inGame = true;
	}
	
	private void quit() {
		table.get(this).send(Server.QUIT);
		close();
	}
	
	private void close() {
		running = false;
		players.remove(name);
		table.remove(this);
	}
}
