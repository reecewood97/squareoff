package Networking;

import java.io.*;
import java.util.ArrayList;

import GameLogic.Board;

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
			toClient.writeObject(players);
			toClient.flush();
			while(!inGame) {

			}
			while(inGame) {
				toClient.writeObject((board.getUpdate()));
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
