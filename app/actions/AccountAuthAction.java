package actions;

import global.GlobalContext;

import java.util.Arrays;

import org.jcheng.domain.Account;
import org.jcheng.repository.AccountRepository;
import org.jcheng.service.account.AuthorizationService;

import play.Logger;
import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.Result;

/**
 * @author jcheng
 *
 */
public class AccountAuthAction extends Simple {
	
	private AuthorizationService authorizationService = (AuthorizationService) GlobalContext.getApplicationContext().getBean("authorizationService");
	private AccountRepository accountRepository = (AccountRepository) GlobalContext.getApplicationContext().getBean("accountRepository");	
	
	/* (non-Javadoc)
	 * @see play.mvc.Action#call(play.mvc.Http.Context)
	 */
	@Override
	public Result call(Context ctx) throws Throwable {
		String path = ctx.request().path();
		String authToken = "";
		if ( ctx.request().queryString().get("atkn") != null ) {
			 authToken = Arrays.asList(ctx.request().queryString().get("atkn")).get(0);
		}
		String username = "";
		if ( ctx.request().queryString().get("username") != null ) {
			 username = ctx.request().queryString().get("username")[0];
		}
		
		Logger.debug("path: " + path);
		Logger.debug("authToken: " + authToken);
		Logger.debug("username: " + username);
		
		if ( authorizationService.validateToken(username, authToken) ) {
			return delegate.call(ctx);
		} else {
			Account account = accountRepository.findOneByUsername(username);
			if ( account != null && account.getAuthorizationToken() != null ) {
				Logger.debug("authorization failed for '" + username + "', expect token of '" + account.getAuthorizationToken() + "'");
			}
			return forbidden("not authorized");
		}
	}

}
