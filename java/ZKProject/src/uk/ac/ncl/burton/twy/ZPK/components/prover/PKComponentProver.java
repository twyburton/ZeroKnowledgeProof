package uk.ac.ncl.burton.twy.ZPK.components.prover;

import java.math.BigInteger;
import java.util.List;

public interface PKComponentProver {
	
	/**
	 * Get the commitment value.
	 * @return the commitment value.
	 */
	public BigInteger getCommitment();
	
	/**
	 * Get the response value.
	 * @param c The challenge from the verifier.
	 * @return the response value.
	 */
	public BigInteger getResponse( BigInteger c );
	
	/**
	 * For the ZKP some variables need to be passed from the prover to the verifier
	 * @return List of the passing variables
	 */
	public List<BigInteger> getPassingVariables();

}
