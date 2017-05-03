package uk.ac.ncl.burton.twy.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkConnectionServer extends NetworkConnection {
	
	public NetworkConnectionServer(){
		connectionType = "SERVER";
	}
	
	public NetworkConnectionServer( int port ){
		connectionType = "SERVER";
		this.port = port;
	}
	
	private int port = NetworkConfig.DEFAULT_NETWORK_PORT;

	@Override
	public void run() {
		
		try {
			ServerSocket ss = new ServerSocket(port);
			if( NetworkConfig.LOG_SERVER ) System.out.println("[SERVER] Waiting for connection...");
			Socket s = ss.accept();
			
			if( NetworkConfig.LOG_SERVER ) System.out.println("[SERVER] Connected to " + s.getInetAddress());
			
			connection(s);
			
			s.close();
			ss.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connection_closed = true;
		
	}
	
	
	
}
