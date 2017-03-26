package Networking;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Client Table is held on the server-side and holds a table of Server Receivers and Server Senders.
 * @author David
 *
 */
public class ClientTable {

	private ConcurrentHashMap<ServerReceiver, ServerSender> table;
	
	/**
	 * Creates a new Client Table.
	 */
	public ClientTable() {
		table = new ConcurrentHashMap<ServerReceiver, ServerSender>();
	}
	
	/**
	 * Add a new Server Receiver Server Sender pair for a new client.
	 * @param r The Server Receiver.
	 * @param s The Server Sender.
	 */
	public void add(ServerReceiver r, ServerSender s) {
		table.put(r, s);
	}
	
	/**
	 * Get the corresponding Server Sender to a Server Receiver.
	 * @param r The Server Receiver.
	 * @return The Server Sender.
	 */
	public ServerSender get(ServerReceiver r) {
		return table.get(r);
	}
	
	/**
	 * Sends an object to all clients in the table.
	 * @param obj The object to be sent.
	 */
	public void sendAll(Object obj) {
		for(ServerSender s: table.values())
			s.send(obj);
	}
	
	/**
	 * Gets a collection of all the Server Receivers in the table.
	 * @return A collection of all the Server Receivers in the table.
	 */
	public Collection<ServerReceiver> getReceivers() {
		return table.keySet();
	}
	
	/**
	 * Removes the Server Receiver and Server Sender pair from the table.
	 * @param r The Server Receiver of the pair.
	 */
	public void remove(ServerReceiver r) {
		table.remove(r);
	}
	
	/**
	 * Returns the number of pair entries in the table.
	 * @return The number of pair entries in the table.
	 */
	public int size() {
		return table.size();
	}
}
