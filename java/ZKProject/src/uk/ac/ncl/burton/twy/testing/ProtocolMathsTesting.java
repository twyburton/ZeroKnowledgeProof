package uk.ac.ncl.burton.twy.testing;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.schnorr.SchnorrKey;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class ProtocolMathsTesting {

	@Test
	public void test() {
		
		SecureRandom ran = new SecureRandom();
		
		// == Generate a Cyclic Group ==
		CyclicGroup G = CyclicGroup.generateGroup(256*2);
		BigInteger g = G.getG();
			
		
		BigInteger a = BigInteger.TEN; // BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		BigInteger b = BigInteger.TEN; // BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		
		BigInteger mod = G.getQ();

		// == PART 1 ==
		BigInteger h =  G.generateGenerator();
		BigInteger x1 = RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran); 
		BigInteger x2 = RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		BigInteger r =  RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		
		
		BigInteger c =  g.modPow( x1, mod ).multiply( h.modPow( x2, mod));
		BigInteger u1 = g.modPow(r, mod );
		BigInteger u2 = h.modPow(r, mod );
		BigInteger e =  g.modPow( a, mod ).multiply( c.modPow( r, mod));

		
		// == PART 2 ==
		BigInteger s = RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		BigInteger t = RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		
		
		BigInteger u1d = u1.modPow(s, mod).multiply( g.modPow(t, mod) );
		BigInteger u2d = u2.modPow(s, mod).multiply( h.modPow(t, mod) );
		
			BigInteger es = e.modPow(s, mod);
				BigInteger bs = b.multiply(s);
			BigInteger gbs = g.modPow( bs ,mod).modInverse(mod);
			BigInteger ct = c.modPow(t, mod);
		
		BigInteger ed = es.multiply(gbs).multiply(ct);	
		
		
		// == PART 3 ==
		BigInteger z = RandomZQ(g,G.getQ(),ran);//BigIntegerUtils.randomBetween(BigInteger.ONE, G.getQ(), ran);
		
		
			BigInteger edz = ed.modPow(z, mod);
				BigInteger zx1 = z.multiply(x1);
			BigInteger u1dzx1 = u1d.modPow( zx1 , mod).modInverse(mod);
				BigInteger zx2 = z.multiply(x2);
			BigInteger u2dzx2 = u2d.modPow( zx2 , mod).modInverse(mod);
		
		BigInteger d = edz.multiply(u1dzx1).multiply(u2dzx2).mod(mod);
		
		assert(d.equals(BigInteger.ONE));
		
		
	}
	
	private BigInteger RandomZQ(BigInteger g, BigInteger q, SecureRandom ran){
		return BigIntegerUtils.randomBetween(BigInteger.ONE, q, ran);
	}

}
