package uk.ac.ncl.burton.twyb.PK.network;

public interface NetworkInterface {

	public String receiveMessage();
	public boolean sendMessage(String msg);
	
}
