package uk.ac.ncl.burton.twy.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class SchnorrKeyGeneratorDEVOLD {
	
	public static void main(String args[]){
		
		SecureRandom ran = new SecureRandom();
		ran.setSeed(0L);
		
		SchnorrKey key = SchnorrKey.generateKey(100);
		
		
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
		//r = BigInteger.valueOf(0);
		
		BigInteger t = g.modPow(r, p);
		System.out.println("t: " + t);
		
		// == Challenge ==
		
//		byte[] cb = new byte[1];
//		ran.nextBytes(cb);
//		BigInteger c = new BigInteger(cb);
		
		BigInteger c = BigIntegerUtils.randomBetween(BigInteger.ONE, q, ran);
		c = BigInteger.ONE;
		System.out.println("c: " + c);

		// == Response ==
		BigInteger s = r.add( c.multiply(x) ).mod(q);
		System.out.println("s: " + s);
		
		
		// == VERFIY ==
		System.out.println( g.modPow(s, p) );
//		System.out.println( g.modPow(s, q) );
//		System.out.println( g.modPow(s, q).mod(p) );
//		System.out.println( g.modPow(s, p).mod(q) );
		
		System.out.println();
		
		System.out.println( t.multiply( y ).mod(p) );
//		System.out.println( t.multiply( y ).mod(q) );
		
		//System.out.println( t.multiply( y.modPow(c, p) ).mod(p) );

	}
	

	
}

