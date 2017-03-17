package uk.ac.ncl.burton.twy.zeroKnowledgeProtocols;

public interface Party {

	/**
	 * This is used to generate random values used for a protocol exchange. It must be executed for each new exchange.
	 * 
	 * @return true if values are generated.
	 */
	public boolean generateProofValues();
	
}
