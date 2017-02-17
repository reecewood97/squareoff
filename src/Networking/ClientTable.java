package Networking;

import java.util.concurrent.ConcurrentHashMap;

public class ClientTable {

	private ConcurrentHashMap<ServerReceiver, ServerSender> table;
	
	public ClientTable() {
		table = new ConcurrentHashMap<ServerReceiver, ServerSender>();
	}
	
	public void add(ServerReceiver r, ServerSender s) {
		table.put(r, s);
	}
	
	public void send(ServerReceiver r, Object obj) {
		table.get(r).send(obj);
	}
	
	public void sendAll(Object obj) {
		for(ServerSender s: table.values())
			s.send(obj);
	}
	
	public ServerSender getSender() {
		for(ServerSender s: table.values())
			return s;
		return null;
	}
}
