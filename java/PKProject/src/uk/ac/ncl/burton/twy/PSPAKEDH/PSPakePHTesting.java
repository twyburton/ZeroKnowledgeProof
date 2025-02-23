package uk.ac.ncl.burton.twy.PSPAKEDH;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import uk.ac.ncl.burton.twy.ZKPoK.utils.BigIntegerUtils;
import uk.ac.ncl.burton.twy.crypto.Crypto;
import uk.ac.ncl.burton.twy.crypto.CyclicGroup;

public class PSPakePHTesting {

	
	@Test
	public void testClasses() throws TerminateProtocolException{
		//CyclicGroup G = CyclicGroup.generateGroup(526);
		
		CyclicGroup G = new CyclicGroup("33680600102952208035398759002921405614512347453386877831664502910598801334503563510578692351879162399390086862276169309179556191695479859956859968422612278069691162959494915456905287724443311121368936321971003530362841165822185865521857235717309551867892501108343846889712270350511848896368835826186415615227281018288034485267624286050923304729380930526314479773641544370618373398564819267616859253095458191396968315123130140557166757486130791339808971162623057659998453448493619899823348743987549013735958832383934630931813306565028954801462428341814080635862188603120108165288127113660152805899931368691692095664983"
				,"16840300051476104017699379501460702807256173726693438915832251455299400667251781755289346175939581199695043431138084654589778095847739929978429984211306139034845581479747457728452643862221655560684468160985501765181420582911092932760928617858654775933946250554171923444856135175255924448184417913093207807613640509144017242633812143025461652364690465263157239886820772185309186699282409633808429626547729095698484157561565070278583378743065395669904485581311528829999226724246809949911674371993774506867979416191967315465906653282514477400731214170907040317931094301560054082644063556830076402949965684345846047832491"
				,"6794096134053734336299889675213706055342575295341791600857418647225529839958105331671253734921407477912034588773593633280615627688439372970432707652869023143905757056356525476777653054266415381108663379772572730634544542462807615976865667918817863354031292052591650812691301863538138040971389823048884033428054106038319789581110047968419581127101105989708452181565533471000679691697731163608437935732234992497866200974343200953714083576834604524580138343081871294256925360348999265769057435830329896906913105178758745800749441105313595637637207000358435038304240907232956509939011512597150867526714632631699255549646");
		
		long t0 = System.currentTimeMillis();
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
		long t1 = System.currentTimeMillis();
		System.out.println((t1-t0) + "ms");
		
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
		BigInteger m = g.modPow(a, G.getQ()).multiply( s_idP.modPow(r, G.getQ()) ).mod(G.getQ()) ; // g^a � (H1(sender,receiver,password))^r
		
		
		System.out.println("m: " + m);
		
		// === STEP 2 ===
		BigInteger r_idP = Crypto.hash( sender.add(receiver).add(r_password) ).mod(G.getQ()) ; // (Hashed identity and password)^r
		
		BigInteger B = g.modPow(b, G.getQ());
		
		
		BigInteger Or = BigIntegerUtils.divide(m, r_idP.modPow(r, G.getQ()), G.getQ()).modPow(b, G.getQ());
		
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
