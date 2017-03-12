package Networking;

import java.io.*;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;

public class ServerSender extends Thread {

	private ObjectOutputStream toClient;
	private Board board;
	private boolean running, inGame;
	
	public ServerSender(ObjectOutputStream toClient, Board board) {
		this.toClient = toClient;
		this.board = board;
	}
	
	public void run() {
		inGame = false;
		running = true;
		
		try {
			while(running) {
				sleep(35);
				if(inGame) {
					if(board.getWinner() > -1){
						send(33);
						System.out.println("Sent the winner");
						send(board.getWinner());
						board.setWinner(-1);
						//TODO
					}else if(board.getTurnFlag()) {
						send(34);
						board.setTurnFlag(false);
						}
					else{
						ArrayList<PhysObject> dummy = new ArrayList<PhysObject>();
						dummy.addAll(board.getUpdate()); 
						send(dummy);
					}
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
			close();
		}
	}
	
	public void close() {
		running = false;
		//System.out.println(getName() + " closed.");
	}
	
	public void startGame() {
		send(Server.PLAY);
		inGame = true;
	}
}
