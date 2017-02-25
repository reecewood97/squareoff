package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import GameLogic.Board;
import ai.AI;

public class Server extends Thread {

	public static final int PLAY = 1;
	public static final int QUIT = 2;
	//public static final int... For other operations.
	
	private int port;
	private Board board;
	private boolean running;
	private ServerSocket socket;
	private ArrayList<String> players;
	private ClientTable table;
	
	public Server(int port) {
		this.port = port;
		board = new Board();
		players = new ArrayList<String>();
		socket = null;
		table = new ClientTable();
		start();
	}
	
	public void run() {
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		running = true;
		try {
			int i = 0;
			while(i != 1) { //Hey there! Change the number here to alter how many players you want to connect before the game can start.
				Socket s = socket.accept();

				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				ServerReceiver sr = new ServerReceiver(fromClient, board, players, table);
				sr.start();
				
				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				ServerSender ss = new ServerSender(toClient, board, players);
				ss.start();
				
				table.add(sr, ss);
				System.out.println(i);
				i++;
				//Temp
			}			
				while(true){
					board.input("None");
					try {
						sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
		}
		catch (IOException e) {
			//e.printStackTrace();
			//System.exit(1);
		}
	}
	
	public void close() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void startGame() {
		addAIs();
	
		for(ServerReceiver r: table.getReceivers()) {
			r.startGame();
			table.get(r).startGame();
		}
			
		board.startGame();	
	}
	
	private void addAIs() {
		int maxPlayers = 4;
		int numberOfPlayers = players.size();
		
		for(int i = numberOfPlayers; i < maxPlayers; i++) {
			new AI(i, i, i, board); //Err... I don't know what to do here.
		}
	}
}
