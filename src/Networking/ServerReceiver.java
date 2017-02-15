package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.Move;

public class ServerReceiver extends Thread {
	
	private ObjectInputStream fromClient;
	private Board board;
	private boolean inGame;
	private ArrayList<String> players;
	private ClientTable table;
	
	public ServerReceiver(ObjectInputStream fromClient, Board board, ArrayList<String> players, ClientTable table) {
		this.fromClient = fromClient;
		this.board = board;
		this.players = players; 
		this.table = table;
	}
	
	public void run() {
		inGame = false;
		
		try {
			String name = (String)fromClient.readObject();
			//TODO duplicate name
			if(players.size() < 4) {
				players.add(name);	
			}
			else {
				//TODO too many players.
			}
			
			Object ob;
			while(!inGame && (ob = fromClient.readObject()) != null) {
				if((int)ob == Server.PLAY) {
					table.sendAll(Server.PLAY);
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
