package uk.ac.ncl.burton.twy.schnorr.parties;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.zeroKnowledgeProtocols.Prover;

public class SchnorrProver implements Prover {

	private CyclicGroup cyclicGroup;
	private BigInteger privateKey;
	
	public SchnorrProver( CyclicGroup cyclicGroup, BigInteger privateKey ){
		this.cyclicGroup = cyclicGroup;
		this.privateKey = privateKey;
	}

	// Value for protocol
	private BigInteger r = null;
	
	@Override
	public boolean generateProofValues() {
		// Generate r for commitment
		SecureRandom ran = new SecureRandom();
		
		byte[] rb = new byte[50];
		ran.nextBytes(rb);
		r = new BigInteger(rb).abs();
		
		return true;
	}
	
	
	public BigInteger getCommitment(){
		if ( r == null ) throw new NullPointerException("Must generate proof values first");
		return cyclicGroup.getG().modPow(r, cyclicGroup.getP() );	
	}
	
	
	public BigInteger getResponse( BigInteger c ){
		if ( r == null ) throw new NullPointerException("Must generate proof values first");
		return r.add( c.multiply( privateKey ) ).mod( cyclicGroup.getQ() );
	}

	
}
