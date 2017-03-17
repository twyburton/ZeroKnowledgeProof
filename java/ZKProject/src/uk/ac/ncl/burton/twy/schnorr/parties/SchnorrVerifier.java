package uk.ac.ncl.burton.twy.schnorr.parties;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;
import uk.ac.ncl.burton.twy.zeroKnowledgeProtocols.Verifier;

public class SchnorrVerifier implements Verifier {

	private CyclicGroup cyclicGroup;
	private BigInteger publicKey;
	
	public SchnorrVerifier( CyclicGroup cyclicGroup, BigInteger publicKey ){
		this.cyclicGroup = cyclicGroup;
		this.publicKey = publicKey;
	}

	// Value for protocol
	private BigInteger c = null;
	
	@Override
	public boolean generateProofValues() {
		// Generate challenge
		
		SecureRandom ran = new SecureRandom();
		c = BigIntegerUtils.randomBetween(BigInteger.ONE,  cyclicGroup.getQ() , ran);
		
		return true;
	}
	
	public BigInteger getChallenge(){
		if ( c == null ) throw new NullPointerException("Must generate proof values first");
		return c;
	}
	
	public boolean verify( BigInteger commitment, BigInteger response ){
		if ( c == null ) throw new NullPointerException("Must generate proof values first");
		
		BigInteger eq1 = cyclicGroup.getG().modPow(response, cyclicGroup.getP());	
		BigInteger eq2 = commitment.multiply( publicKey.modPow(c, cyclicGroup.getP()) ).mod(cyclicGroup.getP());
		
		return eq1.equals(eq2);
		
	}
	
}
