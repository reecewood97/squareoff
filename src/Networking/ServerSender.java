package Networking;

import java.io.*;
import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.PhysObject;

/**
 * This Thread sends objects to the client.
 * @author David
 *
 */
public class ServerSender extends Thread {

	private ObjectOutputStream toClient;
	private Board board;
	private boolean inGame;
	private Server server;
	private boolean sentPlayers = false;
	
	/**
	 * Creates a new Server Sender.
	 * @param toClient The ObjectOutputStream to the Client.
	 * @param board The server-side board.
	 */
	public ServerSender(ObjectOutputStream toClient, Board board, Server server) {
		this.toClient = toClient;
		this.board = board;
		this.server = server;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		inGame = false;
		
		try {
			while(true) {
				sleep(35);
				if(inGame) {
					if (!sentPlayers){
						send(35);
						send(board.getPlayerArray());
						sentPlayers = true;
					}
					
					//Checks if the game is over.
					if(board.getWinner() > -1){

						server.reset();
						System.out.println("Sent the winner");
//						send(board.getWinner());
//						board.setWinner(5);
						//TODO
					}
					//Alerts the board that the turn was ended early.
					else if(board.getTurnFlag()) {
						send(34);
						board.setTurnFlag(false);
					}
					//Sends a "copy" of the game to the client.
					else {
						ArrayList<PhysObject> dummy = new ArrayList<PhysObject>();
						dummy.addAll(board.getUpdate()); 
						send(dummy);
					}
				}
			}
		}
		catch(InterruptedException e) {
			//Thread killed.
		}
	}
	
	/**
	 * Sends an object to the client.
	 * @param ob The object to be sent.
	 */
	public void send(Object ob) {
		try {
			toClient.writeObject(ob);
			toClient.flush();
			toClient.reset();
		}
		catch(IOException e) {
			//The connection broke.
		}
	}
	
	/**
	 * Called when the game is starting. Tells the client threads that the game is starting.
	 */
	public void startGame() {
		send(Server.PLAY);
		inGame = true;
	}
}
