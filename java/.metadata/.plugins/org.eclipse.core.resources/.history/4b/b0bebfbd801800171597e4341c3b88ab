package uk.ac.ncl.burton.twyb.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	public static BigInteger hash( BigInteger num ){
		byte[] bytes = hash( num.toByteArray() );
		
		return new BigInteger( bytes ).abs();
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
	
	
	
	
}

