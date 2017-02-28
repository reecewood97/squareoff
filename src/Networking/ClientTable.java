package Networking;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ClientTable {

	private ConcurrentHashMap<ServerReceiver, ServerSender> table;
	
	public ClientTable() {
		table = new ConcurrentHashMap<ServerReceiver, ServerSender>();
	}
	
	public void add(ServerReceiver r, ServerSender s) {
		table.put(r, s);
	}
	
	public ServerSender get(ServerReceiver r) {
		return table.get(r);
	}
	
	public void sendAll(Object obj) {
		for(ServerSender s: table.values())
			s.send(obj);
	}
	
	public Collection<ServerSender> getSenders() {
		return table.values();
	}
	
	public Collection<ServerReceiver> getReceivers() {
		return table.keySet();
	}
	
	public void remove(ServerReceiver r) {
		table.remove(r);
	}
}
