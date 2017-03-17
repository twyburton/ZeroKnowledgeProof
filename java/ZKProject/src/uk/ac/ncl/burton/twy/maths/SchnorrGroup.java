package uk.ac.ncl.burton.twy.maths;

import java.math.BigInteger;
import java.security.SecureRandom;

import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

@Deprecated
public class SchnorrGroup {

	
	//https://en.wikipedia.org/wiki/Schnorr_group
	
		private BigInteger q; // Prime Order
		private BigInteger p; // Large Prime
		private BigInteger g; // Generator
		
		public SchnorrGroup( BigInteger p , BigInteger q, BigInteger g){
			this.p = p;
			this.q = q;
			this.g = g;
		}
		
		public BigInteger getP(){
			return p;
		}
		
		public BigInteger getQ(){
			return q;
		}
		
		public BigInteger getG(){
			return g;
		}
		
		public BigInteger generateGenerator(){
			
			BigInteger two = BigInteger.valueOf(2);
			SecureRandom ran = new SecureRandom();
			BigInteger g = null;
			
			// Generate g
			while ( true ){
				BigInteger h = BigIntegerUtils.randomBetween( BigInteger.ONE, p.subtract(BigInteger.ONE), ran);
				
				g = h.modPow(two, p);
				
				if( !this.g.equals(g)){
					if( !g.equals( BigInteger.ONE)){
						break;
					}
				}
	
			}
			
			return g;
		}
		
		public static SchnorrGroup generateGroup( int bitLength ){
			
			SecureRandom ran = new SecureRandom();
			BigInteger two = BigInteger.valueOf(2);
			
			BigInteger q = null;
			BigInteger p = null;

			// Generate p and q
			//System.out.println("GENERATING PRIME NUMBERS...");
			while (true){
				
				// Generate Prime Order
				q = BigInteger.probablePrime(bitLength, ran);
				// Generate Large Prime
				p = q.multiply( two ).add(BigInteger.ONE);
				
				if( p.isProbablePrime(100) ){
					break;
				}
				
			}
				
			BigInteger g = null;
			
			// Generate g
			while ( true ){
				BigInteger h = BigIntegerUtils.randomBetween( BigInteger.ONE, p.subtract(BigInteger.ONE), ran);
				
				g = h.modPow(two, p);
				
				if( !g.equals( BigInteger.ONE)){
					break;
				}
				
			}
			
			
			
			SchnorrGroup G = new SchnorrGroup( p , q , g );
			return G;
			
			
		}
}
