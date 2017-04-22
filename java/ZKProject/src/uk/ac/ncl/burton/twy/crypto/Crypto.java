package uk.ac.ncl.burton.twy.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	public static BigInteger hash( BigInteger num ){
		
		return new BigInteger( hash(num.toString()) );
	}
	
	public static byte[] hash( byte[] bytes ){
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
	        return md.digest();
	        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
        
	}
	
	public static byte[] hash( String str ){
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
	        return md.digest();
	        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
        
	}
	
	
	/*public static byte[] bigIntegerToAESKey(BigInteger x ){
		
		
	}*/
	
	
	public static BigInteger divide( BigInteger x, BigInteger y, BigInteger mod ){
		// x/y = x*y^-1
		
		BigInteger n1 = BigInteger.valueOf(-1);	
		return x.multiply( y.modPow(n1, mod) );
	}
	
	public static BigInteger addList( List<BigInteger> list ){
		BigInteger x = BigInteger.ZERO;
		for( int i = 0 ; i < list.size(); i++ ){
			x = x.add(list.get(i));
		}
		return x;
	}
	
	public static BigInteger multiplyList( List<BigInteger> list ){
		BigInteger x = BigInteger.ONE;
		for( int i = 0 ; i < list.size(); i++ ){
			x = x.multiply(	list.get(i) );
		}
		return x;
	}
	
}

