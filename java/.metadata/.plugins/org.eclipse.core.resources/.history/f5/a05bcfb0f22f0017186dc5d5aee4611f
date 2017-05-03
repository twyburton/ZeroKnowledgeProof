package uk.ac.ncl.burton.twyb.crypto;

import java.math.BigInteger;

public class EEAResult {
	
	/*	
	 * 	GCD(x,y) = d
	 * 
	 * 	d = x*s + y*t
	 * 
	 */
	
	private BigInteger d;
	private BigInteger x;
	private BigInteger s;
	private BigInteger y;
	private BigInteger t;
	
	public EEAResult(BigInteger d, BigInteger x, BigInteger s, BigInteger y, BigInteger t){
		this.d = d;
		this.x = x;
		this.s = s;
		this.y = y;
		this.t = t;
	}
	
	public BigInteger getD(){
		return d;
	}
	
	public BigInteger getX(){
		return x;
	}
	
	public BigInteger getS(){
		return s;
	}
	
	public BigInteger getY(){
		return y;
	}
	
	public BigInteger getT(){
		return t;
	}
	
	public String toString(){
		return d + "=" + x + "*" + s + " + " + y + "*" + t;
	}
	
}
