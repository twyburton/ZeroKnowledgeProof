package uk.ac.ncl.burton.twyb.PK;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.ac.ncl.burton.twyb.PK.components.PKComponentVerifier;
import uk.ac.ncl.burton.twyb.crypto.CyclicGroup;


public class PKVerifier {

	private String PK_id;
	
	private boolean proofSuccessful = false; // This is set to true if the proof is completed successfully
	public boolean isProofSucessful(){
		return proofSuccessful;
	}
	
	private CyclicGroup G;
	public CyclicGroup getGroup(){
		return G;
	}
	
	/*@Deprecated
	public PKVerifier(CyclicGroup G, int numberComponents){
		this.G = G;
		
		for( int i = 0 ; i < numberComponents; i++ ){
			//components.add( new PKComponentVerifier(G));
		}
	}*/
	
	public PKVerifier(CyclicGroup G, String PK_id, List<PKComponentVerifier> components ){
		this.G = G;
		this.PK_id = PK_id;
		this.components = components;
		
		
		//for( int i = 0 ; i < numberComponents; i++ ){
			//components.add( new PKComponentVerifier(G));
		//}
		
		
	}
	
	public static PKVerifier getInstance( String initalisationJSON ){
		
		// == TODO ==
		// Set modulus
		// Set up number of components with ids
		
		
		// == JSON PROCESS ==
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(initalisationJSON);
			
			String pk_id =  (String)obj.get("PK_id") ;
			
			
			// -- Setup cyclic group --
			JSONObject group = (JSONObject) ((JSONObject)obj).get("group");
			BigInteger g = new BigInteger( (String) ((JSONObject)group).get("generator") );
			BigInteger q = new BigInteger( (String) ((JSONObject)group).get("modulus") );
			CyclicGroup G = new CyclicGroup( null, q , g);
			
			// -- Setup components --
			JSONArray componentsArray = (JSONArray) ((JSONObject)obj).get("components");
			int nComponents = componentsArray.size();
			
			List<PKComponentVerifier> components = new ArrayList<PKComponentVerifier>();
			for( int i = 0 ; i < nComponents; i++ ){
				
				JSONObject compData = (JSONObject) componentsArray.get(i);
				String comp_id = (String) ((JSONObject)compData).get("component_id");
				int nBases = (int)(long)(Long) ((JSONObject)compData).get("nBases");
				
				components.add( new PKComponentVerifier(G,UUID.fromString(comp_id), nBases));
				
			}
			
			PKVerifier victor = new PKVerifier( G, pk_id , components );
			
			return victor;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	private List<PKComponentVerifier> components = new ArrayList<PKComponentVerifier>();
	
	/**
	 * Get the challenge value. Also ensures the challenge value is set for all components
	 * @return
	 */
	private BigInteger getChallenge(){
		
		BigInteger challenge = components.get(0).getChallenge();
		
		for( int i = 1; i < components.size(); i++ ){
			components.get(i).setChallenge(challenge);
		}
		
		return challenge;
	}
	
	
	
	// == JSON Text ==
	public String getJSONChallenge( String JSONcommitment ){
		
		try {
			// == JSON PROCESS ==
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(JSONcommitment);
			
			PK_id =  (String)obj.get("PK_id") ;
			
			// == JSON output ==
			String json = "";
			
			json += "{\n";
				json += "\t\"PK_id\":\"" + PK_id + "\",\n";
				json += "\t\"protocol_version\":" + Arrays.toString(PKConfig.PROTOCOL_VERSION) + ",\n";
				json += "\t\"step\":\"challenge\",\n";
				json += "\t\"components\":[\n";
			
						json += "\t\t{\n";
							json += "\t\t\t\"c\":\"" +  getChallenge() + "\",\n";
							json += "\t\t\t\"component_id\":\"" + components.get(0).getComponentID() + "\"\n";
						json += "\t\t}";
						
				json += "\t],\n";
				json += "\t\"time\":" + (System.currentTimeMillis()/1000) + "\n";
			json += "}\n";
			
			return json;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String getJSONOutcome( String JSONcommitment, String JSONresponse, String JSONpassing ){
		
		boolean successful = true;
		
		try {
			// == JSON PROCESS ==
			JSONParser parser = new JSONParser();
			JSONObject jCommitment = (JSONObject) parser.parse(JSONcommitment);
			JSONObject jResponse = (JSONObject) parser.parse(JSONresponse);
			JSONObject jPassing = (JSONObject) parser.parse(JSONpassing);
			
			String PK_id_commitment =  (String)jCommitment.get("PK_id") ;
			String PK_id_response =  (String)jCommitment.get("PK_id") ;
			String PK_id_passing =  (String)jCommitment.get("PK_id") ;
			
			
			if( !( PK_id.equals(PK_id_commitment) && PK_id.equals(PK_id_response) && PK_id.equals(PK_id_passing)) ){
				successful = false;
			}
			
			
			for( int i = 0 ; i < components.size() ; i++){
				
				JSONObject commitmentComps = (JSONObject) ((JSONArray)jCommitment.get("components")).get(i);
				JSONObject responseComps = (JSONObject) ((JSONArray)jResponse.get("components")).get(i);
				
				BigInteger t = new BigInteger((String)commitmentComps.get("t"));
				// Ger response list
				JSONArray sList =  (JSONArray)responseComps.get("s");
				List<BigInteger> responseList = new ArrayList<BigInteger>();
				for( int j = 0 ; j < sList.size(); j++){
					responseList.add( new BigInteger((String) sList.get(j)) );
				}
				
				// Get base list
				JSONObject passingComps =  (JSONObject) ((JSONArray)jPassing.get("components")).get(i);
				JSONArray psArr =  (JSONArray)passingComps.get("bases");
				BigInteger passingValue = new BigInteger((String)passingComps.get("value"));

				List<BigInteger> passingBasesList = new ArrayList<BigInteger>();
				for( int j = 0 ; j < psArr.size(); j++){
					passingBasesList.add( new BigInteger((String) psArr.get(j)) );
				}
				
				if (! components.get(i).verify(passingBasesList, responseList, t, passingValue ) ){
					successful = false;
				}
				
				//List<BigInteger> bases, List<BigInteger> responses, BigInteger commitment, BigInteger value 
				
			}
			
			
			int outcome = 0;
			if( successful ){
				outcome = 1;
				proofSuccessful = true;
				if( PKConfig.PRINT_PK_LOG ) System.out.println("Proof Succeeded!");
			} else {
				if( PKConfig.PRINT_PK_LOG ) System.out.println("Proof Failed!");
			}
			
			// == JSON output ==
			String json = "";
			
			json += "{\n";
				json += "\t\"PK_id\":\"" + PK_id + "\",\n";
				json += "\t\"protocol_version\":" + Arrays.toString(PKConfig.PROTOCOL_VERSION) + ",\n";
				json += "\t\"step\":\"outcome\",\n";
				json += "\t\"outcome\":" + outcome + ",\n";
				json += "\t\"time\":" + (System.currentTimeMillis()/1000) + "\n";
			json += "}\n";
			
			return json;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Get the value of the component at index. Zeroth indexed.
	 * @param componentIndex
	 * @return the value
	 */
	public BigInteger getValue( int componentIndex ){
		
		if( proofSuccessful ){
			return components.get(componentIndex).getValue();
		} else return null;
		
	}
	
	/**
	 * Get a base value at index baseIndex for component at component index. Zeroth indexed.
	 * @param componentIndex
	 * @param baseIndex
	 * @return
	 */
	public BigInteger getBase( int componentIndex, int baseIndex ){
		if( proofSuccessful ){
			return components.get(componentIndex).getBases().get(baseIndex);
		} else return null;
	}
}
