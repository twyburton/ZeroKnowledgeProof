package uk.ac.ncl.burton.twy.ZPK.components.prover;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.ac.ncl.burton.twy.ZPK.PKConfig;
import uk.ac.ncl.burton.twy.ZPK.components.PKComponentType;
import uk.ac.ncl.burton.twy.maths.CyclicGroup;

public abstract class PKComponentProverBasic implements PKComponentProver {
	
	/**
	 * The component id. Used for linking components
	 */
	private UUID ComponentID = UUID.randomUUID();
	public UUID getComponentID(){
		return ComponentID;
	}

	/**
	 * The component type (e.g. Alpha, Beta, Gamma...). Used for networking
	 */
	protected PKComponentType type;
	public PKComponentType getComponentType(){
		return type;
	}
	protected abstract void setComponentType();
	
	/**
	 * True if a commitment has been generated to prevent a new commitment being generated.
	 */
	private boolean hasGeneratedTValue = false;
	
	/**
	 * True if a response has been generated to prevent a new response being generated.
	 */
	private boolean hasGeneratedSValue = false;
	
	protected CyclicGroup G = null;
	
	private BigInteger tValue;
	private BigInteger sValue;
	
	PKComponentProverBasic( CyclicGroup G ){
		this.G = G;
		setComponentType();
	}
	
	@Override
	public final BigInteger getCommitment() {
		
		if( !hasGeneratedTValue ){ // Prevent T value from being generated twice
			tValue = generateTValue();
			hasGeneratedTValue = true;
		}
		
		if( PKConfig.PRINT_PK_COMPONENTS_LOG ) System.out.println(ComponentID + " Commitment: " + tValue );
		
		return tValue;
	}
	protected abstract BigInteger generateTValue();

	
	@Override
	public final BigInteger getResponse(BigInteger c) {
		
		if( !hasGeneratedSValue ){ // Prevent S value from being generated twice
			sValue = generateSValue( c );
			hasGeneratedSValue = true;
		}
		
		if( PKConfig.PRINT_PK_COMPONENTS_LOG ) System.out.println(ComponentID + " Response: " + sValue );
		
		return sValue;
	}
	protected abstract BigInteger generateSValue( BigInteger c );


	
	/* USED BY SUB CLASSES TO SET VALUES FOR t AND s */
	@Deprecated
	protected final void setTValue( BigInteger tValue){
		this.tValue = tValue;
	}
	@Deprecated
	protected final void setSValue( BigInteger sValue){
		this.sValue = sValue;
	}
	
	
	// == PASSING VARIABLES ==
	// The passing variables are the common values that both the prover and verifier have
	protected List<BigInteger> passingVariables = new ArrayList<BigInteger>();
	
	@Override
	public final List<BigInteger> getPassingVariables(){
		passingVariables.clear();
		generatePassingVariables();
		return passingVariables;
	}
	protected abstract void generatePassingVariables();
	
}
