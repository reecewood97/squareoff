package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.PhysObject;
import Graphics.Screen;

/**
 * The client receiver class. This is a thread that waits for a message from the server before relaying it to the Board.
 * @author djs568
 *
 */
public class ClientReceiver extends Thread {
	
	private ObjectInputStream server;
	private Board board;
	private Queue q;
	private boolean running, inGame;
	private Screen ui;
	private ArrayList<String> players;
	private Socket socket;

	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen ui, Socket socket) {
		this.server = server;
		this.board = board;
		this.ui = ui;
		this.socket = socket;
		players = new ArrayList<String>();
	}
	
	/**
	 * Run method for the thread.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		
		running = true;
		inGame = false;
		
		try {
			Object ob;
			ArrayList<PhysObject> check = new ArrayList<PhysObject>();
			while(running && (ob = server.readObject()) != null) {
				if(ob.getClass().isInstance(Server.DISCONNECT) && (int)ob == Server.DISCONNECT) {
					socket.close();
				}
				if(inGame) {
					if(ob.getClass().isInstance(check)) {
						board.setObjects((ArrayList<PhysObject>) ob);
						ui.updateSBoard();
					}
					else if ((int)ob == 33){
						//running = false;
						int winner = 3;//This needs to be a read in
						board.setWinner(winner);
						ui = new Screen(board,q,"");
					}
				}
				else {
					if(ob.getClass().isInstance(players)) {
						players = (ArrayList<String>) ob;
					}
					else if((int)ob == Server.PLAY) {
						inGame = true;
						ui.setVisible();
					}
				}
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException e) {
			//e.printStackTrace();
			close();
		}
	}

	/**
	 * Terminates the while loop that checks for a new line from the server and closes the BufferedReader.
	 */
	private void close() {
		running = false;
	}
	
	public ArrayList<String> getPlayers() {
		return players;
	}
	
	public boolean inGame() {
		return inGame;
	}
}







