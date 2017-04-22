package uk.ac.ncl.burton.twyb.ZKPoK.network;

public interface NetworkInterface {

	public String receiveMessage();
	public boolean sendMessage(String msg);
	
}
