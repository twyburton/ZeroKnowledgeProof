package uk.ac.ncl.burton.twy.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;


public class SchnorrKey {

	private BigInteger privateKey;
	private BigInteger publicKey;
	private CyclicGroup G; // Cyclic Group
	
	SchnorrKey( BigInteger pri, BigInteger pub, CyclicGroup G){
		this.privateKey = pri;
		this.publicKey = pub;
		this.G = G;
	}
	
	public BigInteger getPrivateKey(){
		return privateKey;
	}
	
	public BigInteger getPublicKey(){
		return publicKey;
	}
	
	/**
	 * @return Cyclic group prime order
	 */
	public BigInteger getQ(){
		return G.getQ();
	}
	
	/**
	 * 
	 * @return Cyclic group prime
	 */
	public BigInteger getP(){
		return G.getP();
	}
	
	/**
	 * 
	 * @return Cyclic group generator
	 */
	public BigInteger getG(){
		return G.getG();
	}
	
	
	/**
	 * 
	 * @return The cyclic group
	 */
	public CyclicGroup getGroup(){
		return G;
	}
	
	
	
	
	// === GENERATE KEY ===
	/**
	 * Generate a schnorr key given a bit length for the group prime order
	 * 
	 * @param bitLength the bit length for the group prime order
	 * @return the schnorr key
	 */
	public static SchnorrKey generateKey( int bitLength ){
		
		SecureRandom ran = new SecureRandom();
		
		CyclicGroup G = CyclicGroup.generateGroup( bitLength );
		
		// KEYS
		BigInteger x = BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ() , ran); // Private key
		BigInteger y = G.getG().modPow(x, G.getP() ); // Public key
		
		SchnorrKey key = new SchnorrKey(x,y,G);
		return key;
		
	}
	
	/**
	 * Generate a schnorr key given a cyclic group G
	 * @param G The cyclic group
	 * @return The schnorr key
	 */
	public static SchnorrKey generateKey( CyclicGroup G  ){
		
		SecureRandom ran = new SecureRandom();
		
		// KEYS
		BigInteger x = BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ() , ran); // Private key
		BigInteger y = G.getG().modPow(x, G.getP() ); // Public key
		
		SchnorrKey key = new SchnorrKey(x,y,G);
		return key;
		
	}
}
