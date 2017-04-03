package uk.ac.ncl.burton.twyb.PK.network;

import static org.junit.Assert.*;

import org.junit.Test;

public class NetworkTesting {

	@Test
	public void test() {

		NetworkConnectionServer server = new NetworkConnectionServer();
		Thread serverThread = new Thread(server);
		serverThread.start();
		
		NetworkConnectionClient client = new NetworkConnectionClient("localhost");
		Thread clientThread = new Thread(client);
		clientThread.start();
		
		
		client.sendMessage("TEST C->S");
		server.sendMessage("TEST S->C");
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("<SERVER> " + server.receiveMessage());
		System.out.println("<CLIENT> " + client.receiveMessage());
		
		
		while( !server.isConnectionClosed() && !client.isConnectionClosed() ){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
