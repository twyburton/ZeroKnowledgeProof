package uk.ac.ncl.burton.twy.zeroKnowledgeProtocols;

import java.math.BigInteger;

public interface Verifier extends Party {

	/**
	 * Get the challenge value
	 * 
	 * @return The challenge value
	 */
	public BigInteger getChallenge();
	
	/**
	 * Verify the proof
	 * 
	 * @param commitment The commitment value from the prover
	 * @param response The response value from the prover
	 * @return A boolean whether the proof has been verified
	 */
	public boolean verify( BigInteger commitment, BigInteger response );
	
}
