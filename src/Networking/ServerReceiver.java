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
	
	public ServerReceiver(ObjectInputStream fromClient, Board board, ArrayList<String> players, ClientTable table) {
		this.fromClient = fromClient;
		this.board = board;
		this.players = players; 
		this.table = table;
	}
	
	public void run() {
		running = true;
		inGame = false;
		
		try {
			String name = (String)fromClient.readObject();
			if(players.contains(name)) {
				//TODO 
			}
			else if(players.size() < 4) 
				players.add(name);	
			else {
				//TODO 
			}
			
			Object ob;
			while(running && !inGame && (ob = fromClient.readObject()) != null) {
				if((int)ob == Server.PLAY) {
					table.sendAll(Server.PLAY);
					inGame = true;
					board.startGame();
					System.out.println("return");
				}
				else if((int)ob == Server.QUIT) 
					quit();
			}
			
			
			Object input;
			while(running && inGame && (input = fromClient.readObject()) != null) {
				if(input.getClass().isInstance("Sup?"))
					board.input((String)input);
				else if((int)input == Server.QUIT) 
					quit();
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
	
	private void quit() {
		table.send(this, Server.QUIT);
		running = false;
	}
}
