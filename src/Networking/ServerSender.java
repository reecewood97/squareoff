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
		inGame = true;
		
		try {
			while(!inGame) {
				for(String name: players) {
					toClient.writeObject(name);
					toClient.flush();
				}
				toClient.writeObject(0);
				toClient.flush();
				sleep(1000);
			}
			
			while(inGame) {
				ArrayList<PhysObject> x = (board.getUpdate()); 
				//System.out.println(x);
	
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
