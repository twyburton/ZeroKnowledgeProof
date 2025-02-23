package uk.ac.ncl.burton.twy.network;

import java.io.IOException;
import java.net.Socket;

public class NetworkConnectionClient extends NetworkConnection {

	
	public NetworkConnectionClient(){
		connectionType = "CLIENT";
	}
	
	public NetworkConnectionClient( String address ){
		this.address = address;
		connectionType = "CLIENT";
	}
	
	public NetworkConnectionClient( String address, int port ){
		this.address = address;
		connectionType = "CLIENT";
	}
	
	@Override
	public void run() {
		
		try {
			
			if( NetworkConfig.LOG_CLIENT ) System.out.println("[CLIENT] Connecting to "+ address + "...");
			Socket s = new Socket(address,port);
			if( NetworkConfig.LOG_CLIENT ) System.out.println("[CLIENT] Connected");
			
			connection(s);
			
			s.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connection_closed = true;
		
	}
	
	private String address = "localhost";
	public void setAddress( String address ){
		this.address = address;
	}
	public String getAddress(){
		return address;
	}
	
	private int port = NetworkConfig.DEFAULT_NETWORK_PORT;

}
