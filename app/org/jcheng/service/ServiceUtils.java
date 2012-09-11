package org.jcheng.service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

import javax.xml.bind.DatatypeConverter;

/**
 * @author jcheng
 *
 */
public final class ServiceUtils {
	
	public static final String MD5 = "MD5";
	public static final String SHA_256 = "SHA-256";
	public static final String SHA_512 = "SAH-512";
	
	private static final HashSet<String> HASH_ALGOS = new HashSet<String>();
	{
		HASH_ALGOS.add("MD5");
		HASH_ALGOS.add("SHA-256");
		HASH_ALGOS.add("SHA-512");
	}
	
	private ServiceUtils() { }
	
	public static final String hash(String input, String code) {
		if ( !HASH_ALGOS.contains(code) ) {
			code = "SHA-256";
		}
		
		MessageDigest md = null;
		try { 
			md = MessageDigest.getInstance(code);
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException(e);
		}
		md.update(input.getBytes());
    	return DatatypeConverter.printHexBinary(md.digest());
	}

}
