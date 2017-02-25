package Networking;

import java.io.*;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;

public class ServerSender extends Thread {

	private ObjectOutputStream toClient;
	private Board board;
	private ArrayList<String> players;
	private boolean running, inGame;
	
	public ServerSender(ObjectOutputStream toClient, Board board, ArrayList<String> players) {
		this.toClient = toClient;
		this.board = board;
		this.players = players;
	}
	
	public void run() {
		inGame = false;
		running = true;
		
		try {
			while(running && !inGame) {
				toClient.writeObject(players);
				toClient.flush();
				toClient.reset();
				sleep(1000);
			}
		
			while(running && inGame) {
				if(board.getWinner() > -1){
					toClient.writeObject(33);
					toClient.flush();
					System.out.println("Sent the winner");
					toClient.writeObject(board.getWinner());
					toClient.flush();
					board.setWinner(-1);
				}
				else{
					ArrayList<PhysObject> x = (board.getUpdate()); 
					toClient.writeObject(x);
					toClient.flush();
					toClient.reset();
					sleep(40);
				}
			}
			
			toClient.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void send(Object ob) {
		try {
			if(ob.getClass().isInstance(Server.PLAY) && (int)ob == Server.PLAY)
				inGame = true;
			if(ob.getClass().isInstance(Server.QUIT) && (int)ob == Server.QUIT)
				running = false;	
			toClient.writeObject(ob);
			toClient.flush();
			toClient.reset();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() {
		inGame = true;
		inGame = false;
		try {
			toClient.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public boolean inGame() {
		return inGame;
	}
}
