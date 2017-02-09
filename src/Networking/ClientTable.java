package Networking;

import java.util.ArrayList;

public class ClientTable {

	private ArrayList<ClientSender> table;
	
	public ClientTable() {
		table = new ArrayList<ClientSender>();
	}
	
	public void send(int i, Object obj) {
		table.get(i).send(obj);
	}
	
	public void sendAll(Object obj) {
		for(ClientSender s: table) 
			s.send(obj);
	}
}
