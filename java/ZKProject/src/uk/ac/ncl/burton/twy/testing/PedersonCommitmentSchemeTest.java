package uk.ac.ncl.burton.twy.testing;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.schnorr.SchnorrKey;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class PedersonCommitmentSchemeTest {

	@Test
	public void pedersonTest() {
		
		CyclicGroup G = CyclicGroup.generateGroup(200);
		//SchnorrKey key = SchnorrKey.generateKey(G);
	
		// RANDOM
		SecureRandom ran = new SecureRandom();
		

		
		
		
		// == Commitment Phase ==
		BigInteger m = BigInteger.valueOf(999); // SECRET VALUE
		
		// 1
		BigInteger g = G.getG();
		BigInteger h = G.generateGenerator();
		
		// 2
		BigInteger r = BigIntegerUtils.randomBetween(BigInteger.ONE,  G.getP() , ran);
		BigInteger c = g.modPow( r, G.getP()).multiply( h.modPow(m, G.getP())).mod( G.getP() );
		
		// == Reveal Phase ==
		BigInteger eq1 = g.modPow(r, G.getP()).multiply( h.modPow(m, G.getP())).mod(G.getP());
		BigInteger eq2 = c;
		
		System.out.println( eq1.equals(eq2) );
		assertTrue(eq1.equals(eq2));
		
		
		
		
		
		
		
		
		/*
		// Commitment g^r1 * h^r0
		BigInteger commitment = key.getG().modPow(a, key.getP()).multiply(h.modPow(r0, key.getP())).mod(key.getP());
		System.out.println("Commitment: " + commitment);
		
		
		// Challenge
		BigInteger c = BigIntegerUtils.randomBetween(BigInteger.ONE,  key.getQ() , ran);
		System.out.println("Challenge: " + c);
		
		// Response
		BigInteger res = r0.add( c.multiply(a) ).mod( key.getQ() );
		System.out.println("Response: " + res);
		
		// VERIFY
		BigInteger eq1 = commitment;
		BigInteger eq2 = commitment;
		
		System.out.println( eq1.equals(eq2) );
	*/
	}

}
