package uk.ac.ncl.burton.twy.zeroKnowledgeProtocols;

import java.math.BigInteger;

public interface Prover extends Party {
	
	/**Get the commitment value
	 * 
	 * @return The commitment value
	 */
	public BigInteger getCommitment();
	
	/**Get the response value.
	 * 
	 * @param challenge The challenge value from the verifier
	 * @return The response value
	 */
	public BigInteger getResponse( BigInteger challenge );

}
