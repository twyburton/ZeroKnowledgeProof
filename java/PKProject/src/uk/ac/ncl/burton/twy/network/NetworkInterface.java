package uk.ac.ncl.burton.twy.network;

public interface NetworkInterface {

	public String receiveMessage();
	public boolean sendMessage(String msg);
	
}
