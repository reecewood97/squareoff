package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import ai.*;

/**
 * The Server. A thread that creates a ServerSocket that clients can connect to. 
 * Holds an instance of the game board that "copies" of are sent to all connected
 * clients when the game starts.
 * @author djs568
 *
 */
public class Server extends Thread {

	public static final int PLAY = 1;
	public static final int DISCONNECT = 2;
	public static final int PLAYERLIST = 3;
	public static final int EASY_AI = 11;
	public static final int NORMAL_AI = 12;
	public static final int HARD_AI = 13;
	
	private int port, aiDifficulty;
	private Board board;
	private boolean running;
	private ServerSocket socket;
	private ArrayList<String> players;
	private ClientTable table;
	
	public Server(int port) {
		this.port = port;
		board = new Board("map1");
		players = new ArrayList<String>();
		socket = null;
		table = new ClientTable();
		running = false;
		aiDifficulty = EASY_AI;
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
			while(running) {
				Socket s = socket.accept();

				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				ServerReceiver sr = new ServerReceiver(fromClient, board, players, table);
				sr.start();
				
				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				ServerSender ss = new ServerSender(toClient, board);
				ss.start();
				
				table.add(sr, ss);
			}			
		}
		catch (IOException e) {
			System.out.println("ServerSocket closed.");
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
		
		AIManager ais = addAIs();
	
		for(ServerReceiver r: table.getReceivers()) {
			r.startGame();
			table.get(r).startGame();
		}

		board.startGame();	
		new GameLoop(board, ais).start();
	}
	
	private AIManager addAIs() {
		AIManager ais = new AIManager(board);
		for(ServerReceiver s: table.getReceivers()) {
			s.getPlayerName();
		}
		int maxPlayers = 4;
		int numberOfPlayers = players.size();
		
		for(int i = numberOfPlayers + 1; i <= maxPlayers; i++) {
			AI ai;
			switch(aiDifficulty) {
				case NORMAL_AI: ai = new NormalAI(i, 0, i, board);
					break;
				case HARD_AI: ai = new DifficultAI(i, 0, i, board);	
					break;
				default: ai = new EasyAI(i, 0, i, board);
			}
			ais.add(ai);
			String name = "AI " + i;
			players.add(name);
			board.addName(name);
		}
		
		return ais;
	}
	
	public boolean kick(String name) {
		for(ServerReceiver r: table.getReceivers()) {
			if(r.getPlayerName().equals(name)) {
				table.get(r).send(Server.DISCONNECT);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getPlayers() {
		return players;
	}
	
	public void setAIDifficulty(int aiDifficulty) {
		this.aiDifficulty = aiDifficulty;
	}
}
