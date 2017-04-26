package uk.ac.ncl.burton.twyb.ZKPoK.components;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import uk.ac.ncl.burton.twyb.ZKPoK.utils.BigIntegerUtils;
import uk.ac.ncl.burton.twyb.crypto.CyclicGroup;

public final class PKComponentVerifier {
	
	/** The UUID of the component */
	private UUID component_id = UUID.randomUUID();
	/**
	 * Get the UUID of the component
	 * @return the component UUID
	 */
	public UUID getComponentID() { return component_id; }
	
	/** The cyclic group for the proof */
	private CyclicGroup G;
	
	/** The challenge value of the proof */
	private BigInteger challenge;
	
	/** The expected number of bases for the proof */
	private int expectedNumberBases;
	
	public PKComponentVerifier( CyclicGroup G, UUID component_id , int expectedNumberBases){
		this.G = G;
		this.component_id = component_id;
		this.expectedNumberBases = expectedNumberBases;
		generateRandomValues();
	}
	
	/**
	 * Generate the required random values. Run when instantiating the object.
	 */
	private void generateRandomValues(){
		challenge = BigIntegerUtils.randomBetween( BigInteger.ONE, G.getQ() );
	}
	
	/**
	 * Get the challenge value.
	 * @return the challenge value
	 */
	public BigInteger getChallenge(){
		return challenge;
	}
	
	/**
	 * Set the challenge value
	 * @param c the challenge value
	 */
	public void setChallenge( BigInteger c){
		if( c == null ) throw new IllegalArgumentException("The challenge cannot be null");
		
		challenge = c;
	}

	/**
	 * Verification step for the component proof.
	 * @param bases The list of bases
	 * @param responses The list of responses
	 * @param commitment The commitment value
	 * @param value The value of the component
	 * @return true if the verification is successful, otherwise false.
	 */
	public boolean verify( List<BigInteger> bases, List<BigInteger> responses, BigInteger commitment, BigInteger value ){
		// Example: u^s1 g^s2 == t(x)^c
		
		if( bases == null ) throw new IllegalArgumentException("bases cannot be null");
		if( responses == null ) throw new IllegalArgumentException("responses cannot be null");
		if( commitment == null ) throw new IllegalArgumentException("commitment cannot be null");
		if( value == null ) throw new IllegalArgumentException("value cannot be null");
		
		if( bases.size() != expectedNumberBases ) throw new IllegalArgumentException("The number of bases does not match expected. (Received " + bases.size()+ " Expected " + expectedNumberBases+ ")");
		if( bases.size() != responses.size() ) throw new IllegalArgumentException("The number of bases must match the number of responses");
		
		this.value = value;
		this.bases= bases;
		
		BigInteger leftSide = BigIntegerUtils.multiplyBaseExponents(G.getQ(), bases, responses);
		BigInteger rightSide = commitment.multiply( value.modPow(challenge, G.getQ()) ).mod(G.getQ());
		
		return leftSide.equals(rightSide);
		
	}
	
	/**
	 * Saved value for later use
	 */
	private BigInteger value = null;
	/**
	 * Get saved value
	 * @return saved value
	 */
	public BigInteger getValue(){
		if ( value == null ) throw new IllegalStateException("The verification step must take place first");
		return value;
	}
	
	/**
	 * Saved bases values for later use
	 */
	private List<BigInteger> bases = null;
	/**
	 * Get saved bases values
	 * @return saved bases
	 */
	public List<BigInteger> getBases(){
		if ( bases == null ) throw new IllegalStateException("The verification step must take place first");
		return bases;
	}
	
	
	
	
}
