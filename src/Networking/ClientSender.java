package Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Sends objects via a queue to the server.
 * @author David
 *
 */
public class ClientSender extends Thread {

	private ObjectOutputStream server;
	private Queue q;
	private boolean running;
	
	/**
	 * Creates a new Client Sender.
	 * @param server The ObjectOutputStream to the server.
	 * @param q The queue that items are taken from and sent to the server.
	 */
	public ClientSender(ObjectOutputStream server, Queue q) {
		this.server = server;
		this.q = q;
		running = false;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		running = true;
		
		try {
			while(running) {
				//Take from the queue and send it to the server.
				Object obj = q.take();
				server.writeObject(obj);
				server.flush();
				server.reset();
			}

		}
		catch(IOException e) {
			//The socket has closed.
		}
	}
	
	/**
	 * Sends an object to the server.
	 * @param obj The object to be sent to the server.
	 */
	public void send(Object obj) {
		q.offer(obj);
	}
}
