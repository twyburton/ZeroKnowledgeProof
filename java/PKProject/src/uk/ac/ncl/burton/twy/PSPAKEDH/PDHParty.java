package uk.ac.ncl.burton.twy.PSPAKEDH;

import java.math.BigInteger;

public abstract class PDHParty {

	
	
	protected boolean hasGeneratedValues = false;
	
	public abstract void generateValues();
	
	public void checkValues(){
		if(! hasGeneratedValues ){
			generateValues();
		}
	}
	
	
	
	protected BigInteger DHKey = null; // The key generated from the DH.
	public BigInteger getDHKey(){
		return DHKey;
	}
	
	protected BigInteger sessionKey = null; // The session key
	public BigInteger getSessionKey(){
		return sessionKey;
	}
	
}
