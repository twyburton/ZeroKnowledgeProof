package uk.ac.ncl.burton.twy.ZPK.components.prover;

import java.math.BigInteger;

import uk.ac.ncl.burton.twy.ZPK.components.PKComponentType;
import uk.ac.ncl.burton.twy.maths.CyclicGroup;
import uk.ac.ncl.burton.twy.utils.BigIntegerUtils;

public class PKComponentProverAlpha extends PKComponentProverBasic {

	/*
	 * This component is used for proving knowledge of r in the form u = g^r
	 * {PK(r): u = g^r}
	 * 
	 * 
	 * 
	 * 
	 * 
	 *  (Capital values refer to random generated values. Instead of the lower case with a tilde above.)
	 *  (. represents multiplication)
	 * 
	 * 
	 *	Proving PK{(r): u = g^r }
	 * 	
	 *  Commitment: t = g^R
	 *  Challenge: 	c = random
	 *  Response: 	s = R + c.r				AS PART OF RESPONSE u AND g MUST BE SENT
	 *  
	 *  Verification:	g^s == t.(u)^c 	
	 * 
	 * 
	 *	
	 * 
	 * 
	 * 
	 */
	
	// Values for proof
	private final BigInteger u,g,r;
	// THESE SHOULD ONLY BE USED BY OTHER PROVER COMPONENT TYPES
	public BigInteger getValueu(){ return u; }
	public BigInteger getValueg(){ return g; }
	public BigInteger getValuer(){ return r; }
	
	// Random values
	private BigInteger rd;
	// THESE SHOULD ONLY BE USED BY OTHER PROVER COMPONENT TYPES	
	public BigInteger getDValuer(){ return rd; }
	
	
	
	public PKComponentProverAlpha(CyclicGroup G, BigInteger g, BigInteger r ) {
		super(G);
		this.g = g;
		this.r = r;
		this.u = g.modPow(r, G.getP() );
	}


	@Override
	protected BigInteger generateTValue() {
		
		 rd = BigIntegerUtils.randomBetween( BigInteger.ONE, G.getQ() );
		 BigInteger tValue = g.modPow(rd, G.getP() );
		 
		 return tValue;
		
	}

	@Override
	protected BigInteger generateSValue(BigInteger c) {
		
		BigInteger sValue = rd.add( c.multiply(r).mod( G.getQ() ) ).mod( G.getQ() );
		
		return sValue;
		
	}


	@Override
	protected void generatePassingVariables() {
		// ORDER: g,u
		passingVariables.add( g );
		passingVariables.add( u );
	}
	
	@Override
	protected void setComponentType() {
		type = PKComponentType.ALPHA;
	}



	
	
}
