package uk.ac.ncl.burton.twy.testing;

import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.Test;
import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.schnorr.SchnorrKey;
import uk.ac.ncl.burton.twy.schnorr.parties.SchnorrProver;
import uk.ac.ncl.burton.twy.schnorr.parties.SchnorrVerifier;

public class SchnorrProtocolTest {

	@Test
	public void schnorrTest() {
		
		// == Generate a Schnorr key ==
		CyclicGroup G = CyclicGroup.generateGroup(200);
		SchnorrKey key = SchnorrKey.generateKey(G);
		
		// == Setup parties ==
		SchnorrProver peggy = new SchnorrProver( key.getGroup(), key.getPrivateKey() );
		SchnorrVerifier victor = new SchnorrVerifier( key.getGroup(), key.getPublicKey() );
		
		peggy.generateProofValues();
		victor.generateProofValues();
		
		// == Commitment ==
		BigInteger commitment = peggy.getCommitment();
		System.out.println("Commitment: " + commitment);
		
		// == Challenge ==
		BigInteger challenge = victor.getChallenge();
		System.out.println("Challenge: " + challenge);

		// == Response ==
		BigInteger response = peggy.getResponse(challenge);
		System.out.println("Response: " + response);
		
		// == VERFIY ==
		assertTrue(victor.verify(commitment, response));
		
	}

}
