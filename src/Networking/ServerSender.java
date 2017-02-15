package Networking;

import java.io.*;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;

public class ServerSender extends Thread {

	private ObjectOutputStream toClient;
	private Board board;
	private ArrayList<String> players;
	private boolean inGame;
	
	public ServerSender(ObjectOutputStream toClient, Board board, ArrayList<String> players) {
		this.toClient = toClient;
		this.board = board;
		this.players = players;
	}
	
	public void run() {
		inGame = false;
		
		try {
			while(!inGame) {
				toClient.writeObject(players);
				toClient.flush();
				toClient.reset();
				sleep(1000);
			}
			
			while(inGame) {
				ArrayList<PhysObject> x = (board.getUpdate()); 
	
				toClient.writeObject(x);
				toClient.flush();
				toClient.reset();
				sleep(40);
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
	
	public void send(Object obj) {
		try {
			toClient.writeObject(obj);
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
}
