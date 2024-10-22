package uk.ac.ncl.burton.twy.ZKPoK.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NetworkUtils {

	// Methods from: http://stackoverflow.com/a/10380460/6658062
	
	public static  byte[] my_int_to_bb_le(int myInteger){
	    return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(myInteger).array();
	}

	public static int my_bb_to_int_le(byte [] byteBarray){
	    return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	public static  byte[] my_int_to_bb_be(int myInteger){
	    return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(myInteger).array();
	}

	public static int my_bb_to_int_be(byte [] byteBarray){
	    return ByteBuffer.wrap(byteBarray).order(ByteOrder.BIG_ENDIAN).getInt();
	}
	
	
}
