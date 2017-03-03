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
				table.get(this).send(Server.DISCONNECT);
			}
			else if(players.size() < 4){ 
				players.add(name);
				board.addName(name);
			}
			else {
				//table.get(this).send(Server.DISCONNECT);
			}
			
			Object input;
			while(running && (input = fromClient.readObject()) != null) {
				if(inGame) {
					if(input.getClass().isInstance("A String")) {
						board.input((String)input);
					}
				}
				else {
					//TODO
				}
			}
		}
		catch(IOException e) {
			//e.printStackTrace();
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
	
	private void close() {
		running = false;
		players.remove(name);
		table.remove(this);
	}
	
	public String getPlayerName() {
		while(name.equals("")) {
			try {
				Thread.sleep(100);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return name;
	}
}
