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
				boolean aigenned = false;
			
				while(true){
//					if(!aigenned && table.getSender().inGame()) {
//						new AI(0, 0, 0, board).determineState();
//					    aigenned = true;
//					}

					board.input("None");
					try {
						sleep(40);
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
	
	public void close() throws IOException {
		running = false;
		socket.close();
	}
}
