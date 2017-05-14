package uk.ac.ncl.burton.twy.ZKPoK.components;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.ac.ncl.burton.twy.ZKPoK.utils.BigIntegerUtils;
import uk.ac.ncl.burton.twy.crypto.CyclicGroup;


public final class PKComponentProver {

	/** The UUID of the component */
	private final UUID component_id = UUID.randomUUID();
	/**
	 * Get the UUID of the component
	 * @return the component UUID
	 */
	public UUID getComponentID() { return component_id; }
	
	
	/** The cyclic group for the proof */
	private final CyclicGroup G;
	
	/** The list of bases */
	private final List<BigInteger> bases;
	
	/** The list of exponents */
	private List<BigInteger> exponents;
	
	/** The list of random exponents generated by the class */
	private List<BigInteger> randomExponents;
	/** Flag set to true when random exponents are destroyed after the getResponse() method. 
	 * This prevents use of the method after random exponents are set to null. */
	private boolean valuesDestroyed = false;
	
	/** The value of the equation */
	private final BigInteger value;


	private PKComponentProver(final CyclicGroup G, final List<BigInteger> bases, final List<BigInteger> exponents ){
		
		if( bases.size() != exponents.size()){
			throw new IllegalArgumentException("The number of bases must match the number of exponents");
		}
		
		this.bases = bases;
		this.exponents = exponents;
		this.G = G;
		
		// Q
		this.value =  BigIntegerUtils.multiplyBaseExponents(G.getQ(), bases, exponents);
		
		generateRandomValues();
	}

	
	/**
	 * Generate a component prover instance.
	 * @param G the cyclic group
	 * @param bases the list of bases
	 * @param exponents the list of exponents
	 * @return a prover instance
	 */
	public static PKComponentProver generateProver( final CyclicGroup G, final List<BigInteger> bases, final List<BigInteger> exponents){
		
		if( G == null ) throw new IllegalArgumentException("G cannot be null");
		if( bases == null || bases.size() == 0) throw new IllegalArgumentException("bases cannot be null or of length 0");
		if( exponents == null || exponents.size() == 0) throw new IllegalArgumentException("exponents cannot be null or of length 0");
		
		return new PKComponentProver(G,bases,exponents);
	}
	
	/**
	 * Generate component prover instance. This is when there is only 1 base and 1 exponent.
	 * @param G the cyclic group
	 * @param base the base value
	 * @param exponent the exponent value
	 * @return a prover instance
	 */
	public static PKComponentProver generateProver( final CyclicGroup G, final BigInteger base, final BigInteger exponent){
		
		if( G == null ) throw new IllegalArgumentException("G cannot be null");
		if( base == null ) throw new IllegalArgumentException("The base cannot be null");
		if( exponent == null ) throw new IllegalArgumentException("The exponent cannot be null");
		
		List<BigInteger> bases = new ArrayList<BigInteger>();
		List<BigInteger> exponents = new ArrayList<BigInteger>();
		
		bases.add(base);
		exponents.add(exponent);
		
		return new PKComponentProver(G,bases,exponents);
		
	}

	/**
	 * Get a list of the bases
	 * @return the list of bases
	 */
	public List<BigInteger> getBases(){
		return bases;
	}
	
	/**
	 * Get the value of the equation
	 * @return the value of the equation
	 */
	public BigInteger getValue(){
		return value;
	}
	
	/**
	 * Generate the required random values. Run when instantiating the object.
	 */
	private void generateRandomValues(){
		randomExponents = new ArrayList<BigInteger>();
		for( int i = 0 ; i < exponents.size(); i++ ) randomExponents.add(BigIntegerUtils.randomBetween( BigInteger.ONE, G.getP() ));
	}
	
	/**
	 * Get the commitment value
	 * @return the commitment value
	 */
	public BigInteger getCommitment(){
		// Q
		return BigIntegerUtils.multiplyBaseExponents(G.getQ(), bases, randomExponents);
		
	}
	
	/**
	 * Get a list of the response values
	 * @param c the challenge
	 * @return the list of response values
	 */
	public List<BigInteger> getResponse( final BigInteger c ){
		
		if( c == null ) throw new IllegalArgumentException("The challenge cannot be null");
		if( valuesDestroyed ) throw new IllegalStateException("The exponents have already been de-referenced. This method cannot be recalled.");
		
		// s1 = rd + rc			s2 = td + tc
		
		List<BigInteger> s = new ArrayList<BigInteger>();
		
		for( int i = 0 ; i < bases.size(); i++ ){
			
			BigInteger rd = randomExponents.get(i);
			BigInteger r = exponents.get(i);
			
			BigInteger rc = r.multiply(c);
			BigInteger x = rd.add( rc ); 
		
			// P
			x.mod(G.getP());
			
			s.add( x );
		}
		
		
		
		// Destroy  references
		exponents = null;
		randomExponents = null;
		valuesDestroyed = true;
		
		return s;
	}
	
	/**
	 * Get the random exponent value for base i.
	 * This method should only be used for setting the random exponent of another component. This value should never be sent to the other party.
	 * @param index the base index
	 * @return the random exponent
	 */
	BigInteger getRandomExponent( final int index ){
		if( index < 0 || index >= randomExponents.size() ){
			throw new IllegalArgumentException("Index is out of range of exponents");
		}
		
		return randomExponents.get(index);
	}
	
	/**
	 * Set the random exponent to value for base i
	 * @param index the base index 
	 * @param value the value which is being set
	 * @return the previous value at the position
	 */
	BigInteger setRandomExponent( final int index, final BigInteger value ){
		if( index < 0 || index >= randomExponents.size() ){
			throw new IllegalArgumentException("Index is out of range of exponents");
		}
		
		if( value == null ){
			throw new IllegalArgumentException("value cannot be null");
		}
		
		return randomExponents.set(index, value);
	}
}