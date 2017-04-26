package uk.ac.ncl.burton.twyb.ZKPoK.components;

import java.math.BigInteger;

/**
 * This class manages the references for the random values so that no stray references are floating around.
 * The class also prevents users from getting hold of the random values to send.
 * @author twyburton
 *
 */
public final class PKRandomnessManager {

	/**
	 * Transfer a random exponent value between one component to another.
	 * @param comp0 The component to set random exponent value to (Target)
	 * @param index0 The exponent index of the first component
	 * @param comp1 The component to get random exponent value form (Source)
	 * @param index1 The exponent index of the second component
	 * @return
	 */
	public static boolean transferRandomness( final PKComponentProver comp0, final int index0 , PKComponentProver comp1, final int index1 ){
	
		comp0.setRandomExponent(index0, comp1.getRandomExponent(index1) );
		
		return true;
	}
	
	
	/**
	 * Get the random exponent value from two components and multiply by another value. comp0[index0] = comp1[index1] * comp2[index2] * value.
	 * @param comp0 (Target)
	 * @param index0 
	 * @param comp1 (Source)
	 * @param index1
	 * @param comp2  (Source)
	 * @param index3 
	 * @param value The final value to multiply
	 * @return
	 */
	public static boolean transferRandomness2Multiplyx( final PKComponentProver comp0, final int index0 
			, PKComponentProver comp1, final int index1, final PKComponentProver comp2, final int index2, final BigInteger value ){
		
		// comp2 = comp0 * comp1 * value 
				
		BigInteger r1 = comp1.getRandomExponent(index1);
		BigInteger r2 = comp2.getRandomExponent(index2);
		
		comp0.setRandomExponent(index0, r1.multiply(r2).multiply(value) );
		
		return true;
	}
	
}
