package uk.ac.ncl.burton.twy.ZPK.components.verifier;

import java.math.BigInteger;
import java.util.List;

public interface PKComponentVerifier {

	
	public BigInteger getChallenge();
	public void setChallenge( BigInteger newChallenge );
	
	public boolean verify(BigInteger commitment, BigInteger response, List<BigInteger> passingVariables);
	
}
