package uk.ac.ncl.burton.twy.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class SchnorrProtocol {

	public static void main(String[] args) {
		
		SecureRandom ran = new SecureRandom();
		ran.setSeed(0L);
		
		SchnorrKey key = SchnorrKey.generateKey(500);
		
		
		System.out.println("KEY");
		System.out.println("q: " + key.getQ());
		System.out.println("p: " + key.getP());
		System.out.println("g: " + key.getG());
		
		System.out.println("x: " + key.getPrivateKey());
		System.out.println("y: " + key.getPublicKey());
		System.out.println();
		
		BigInteger p = key.getP();
		BigInteger q = key.getQ();
		BigInteger g = key.getG();
		BigInteger x = key.getPrivateKey();
		BigInteger y = key.getPublicKey();
		
		// == Commitment ==
		byte[] rb = new byte[50];
		ran.nextBytes(rb);
		BigInteger r = new BigInteger(rb).abs();
		
		BigInteger t = g.modPow(r, p);
		System.out.println("t: " + t);
		
		// == Challenge ==		
		BigInteger c = BigIntegerUtils.randomBetween(BigInteger.ONE, q, ran);
		System.out.println("c: " + c);

		// == Response ==
		BigInteger s = r.add( c.multiply(x) ).mod(q);
		System.out.println("s: " + s);
		
		
		// == VERFIY ==
		BigInteger eq1 = g.modPow(s, p);	
		BigInteger eq2 = t.multiply( y.modPow(c, p) ).mod(p);
		
		boolean verified = eq1.equals( eq2 );
		System.out.println(verified);
		/*System.out.println( g.modPow(s, p) );	
		System.out.println( t.multiply( y ).mod(p) );*/


	}

}
