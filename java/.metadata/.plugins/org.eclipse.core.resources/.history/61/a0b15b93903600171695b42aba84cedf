package uk.ac.ncl.burton.twy.PSPAKEDH;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import uk.ac.ncl.burton.twy.crypto.Crypto;
import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class PSPakePHTesting {

	
	@Test
	public void testClasses() throws TerminateProtocolException{
		CyclicGroup G = CyclicGroup.generateGroup(526);
			
		BigInteger s_password = BigInteger.valueOf(123456);
		BigInteger r_password = BigInteger.valueOf(123456);
		
		BigInteger sender_id = BigInteger.valueOf(1111);
		BigInteger receiver_id = BigInteger.valueOf(2222);
		
		PDHSender alice = new PDHSender(G,s_password,sender_id,receiver_id);
		PDHReceiver bob = new PDHReceiver(G,r_password,sender_id,receiver_id);
		
		
		BigInteger m = alice.getStep1();
		System.out.println("m: " + m);
		
		BigInteger B = bob.getStep2a();
		BigInteger k = bob.getStep2b(m);
		System.out.println("B: " + B);
		System.out.println("k: " + k);
		
		BigInteger k2 = alice.getStep3(B, k);
		System.out.println("k2: " + k2);

		bob.getStepFinal(k2);
		BigInteger a_sk = alice.getSessionKey();
		BigInteger b_sk = bob.getSessionKey();
		System.out.println("a sk: " + a_sk);
		System.out.println("b sk: " + b_sk);
		
		
		assertTrue(a_sk.equals(b_sk));
		
	}
	
	
	//@Test
	public void test() {
		
		CyclicGroup G = CyclicGroup.generateGroup(256);
		
		BigInteger g = G.getG();
		
		BigInteger a = BigIntegerUtils.randomBetween( BigInteger.ONE, G.getQ() );
		BigInteger b = BigIntegerUtils.randomBetween( BigInteger.ONE, G.getQ() );
		
		BigInteger s_password = BigInteger.valueOf(123456);
		BigInteger r_password = BigInteger.valueOf(123456);
		BigInteger r = BigInteger.valueOf(1234567890);
		
		BigInteger sender = BigInteger.valueOf(1111);
		BigInteger receiver = BigInteger.valueOf(2222);
		
		BigInteger authenticate_salt = BigInteger.valueOf(987654321);
		BigInteger exchange_salt = BigInteger.valueOf(987321);
		
		// === STEP 1 ===
		// Create hash of IDs and Password
		BigInteger s_idP = Crypto.hash( sender.add(receiver).add(s_password) ).mod(G.getQ()) ; // (Hashed identity and password)^r
		// Create m
		BigInteger m = g.modPow(a, G.getQ()).multiply( s_idP.modPow(r, G.getQ()) ).mod(G.getQ()) ; // g^a · (H1(sender,receiver,password))^r
		
		
		System.out.println("m: " + m);
		
		// === STEP 2 ===
		BigInteger r_idP = Crypto.hash( sender.add(receiver).add(r_password) ).mod(G.getQ()) ; // (Hashed identity and password)^r
		
		BigInteger B = g.modPow(b, G.getQ());
		
		
		BigInteger Or = Crypto.divide(m, r_idP.modPow(r, G.getQ()), G.getQ()).modPow(b, G.getQ());
		
		// === STEP 3 ===
		BigInteger Os = B.modPow(a, G.getQ());
		System.out.println("Os: " + Os);
		System.out.println("Or: " + Or);
		
		
		byte[] s_aesKey = Crypto.hash(Os.toString());
		byte[] r_aesKey = Crypto.hash(Or.toString());
		
		for( int i = 0 ; i < s_aesKey.length; i++ ){
			assertTrue( s_aesKey[i] == r_aesKey[i] );
		}
		
		// === STANDARD DH. ===
		/*BigInteger A = g.modPow(a, G.getQ());
		BigInteger B = g.modPow(b, G.getQ());
		
		System.out.println(A.modPow(b, G.getQ()));
		System.out.println(B.modPow(a, G.getQ()));*/
		
		
		
		
	}

}
