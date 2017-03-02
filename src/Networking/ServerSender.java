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
			while(running) {
				if(inGame) {
					if(board.getWinner() > -1){
						//running = false;
						send(33);
						System.out.println("Sent the winner");
						send(board.getWinner());
						board.setWinner(-1);
					}
					else{
						ArrayList<PhysObject> x = new ArrayList<PhysObject>();
						x.addAll(board.getUpdate()); 
						send(x);
						sleep(40);
					}
				}
				else {
					send(players);
					sleep(1000);
				}
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void send(Object ob) {
		try {
			toClient.writeObject(ob);
			toClient.flush();
			toClient.reset();
		}
		catch(IOException e) {
			//e.printStackTrace();
			close();
		}
	}
	
	private void close() {
		running = false;
	}
	
	public boolean inGame() {
		return inGame;
	}
	
	public void startGame() {
		send(Server.PLAY);
		inGame = true;
	}
}
