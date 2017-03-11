package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import GameLogic.Board;


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
			
			setName(name + ": server receiver");
			table.get(this).setName(name + ": server sender");
			
			Object obj;
			while(running && (obj = fromClient.readObject()) != null) {
				if(inGame && obj.getClass().isInstance("")) {
					board.input((String)obj);
				}
				else if(obj.getClass().isInstance(0)) {
					if((int)obj == Server.PLAYERLIST) {
						ArrayList<String> dummy = new ArrayList<String>();
						dummy.addAll(players);
						table.get(this).send(dummy);
					}
				}
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
	
	private void close() {
		running = false;
		players.remove(name);
		board.removeName(name);
		table.get(this).send("I close the server sender.");
		try {
			table.get(this).join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		table.remove(this);
		//System.out.println(getName() + " closed.");
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
