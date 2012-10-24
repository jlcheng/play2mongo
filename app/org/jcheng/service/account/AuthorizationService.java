package org.jcheng.service.account;

/**
 * @author jcheng
 *
 */
public interface AuthorizationService {
	
	String getToken(String username);

	boolean validateToken(String username, String token);
	
}
