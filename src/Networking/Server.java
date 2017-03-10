package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import ai.*;

/**
 * The Server. A thread that creates a ServerSocket that clients can connect to. 
 * Holds an instance of the game board that "copies" of are sent to 
 * @author djs568
 *
 */
public class Server extends Thread {

	public static final int PLAY = 1;
	public static final int DISCONNECT = 2;
	
	private int port;
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
				ServerSender ss = new ServerSender(toClient, board, players);
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
		
		ArrayList<AI> ais = new ArrayList<AI>();
		addAIs(ais);
	
		for(ServerReceiver r: table.getReceivers()) {
			r.startGame();
			table.get(r).startGame();
		}

		board.startGame();	
		new GameLoop(board, ais).start();
	}
	
	private void addAIs(ArrayList<AI> ais) {
		for(ServerReceiver s: table.getReceivers()) {
			s.getPlayerName();
		}
		int maxPlayers = 4;
		int numberOfPlayers = players.size();
		
		for(int i = numberOfPlayers + 1; i <= maxPlayers; i++) {
			AI ai = new NormalAI(i, 0, i, board);
			ais.add(ai);
			players.add("AI " + (i + 1 - numberOfPlayers));
		}
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
}
