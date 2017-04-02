package uk.ac.ncl.burton.twy.ZPK2.components;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class PoKComponentTesting {

	
	@Test
	public void repeatableTest(){
		int n_tests = 100;
		for( int i = 0 ; i < n_tests; i++ ){
			System.out.println("Test: " + (i+1)+"/" + n_tests );
			PoKTesting();
		}
		System.out.println("DONE!");
	}
	
	
	@Test
	public void PoKTesting() {
		
		CyclicGroup G = CyclicGroup.generateGroup(256);
		BigInteger r = BigIntegerUtils.randomBetween( BigInteger.ONE, G.getQ() );
		
		List<BigInteger> bases = new ArrayList<BigInteger>();
		List<BigInteger> exponents = new ArrayList<BigInteger>();
		
		bases.add(G.getG());
		exponents.add(r);
		
		PoKComponentProver peggy = PoKComponentProver.generateProver(G, bases, exponents);
		PoKComponentVerifier victor = new PoKComponentVerifier(G);
		//System.out.println("Value: " + peggy.getValue());
		
		BigInteger t = peggy.getCommitment();
		//System.out.println("Commitment: " + t);
		
		BigInteger c = victor.getChallenge();
		//System.out.println("Challenge: " + c);
		
		List<BigInteger> s = peggy.getResponse(c);
		//for( int i = 0 ; i < s.size(); i++ ){
			//System.out.println("s" + (i+1) + ": " + s.get(i));
		//}
		
		assertTrue( victor.verify(bases, s, t, peggy.getValue()) );
			
	}
	

}