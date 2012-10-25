package actions;

import global.GlobalContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static final Pattern ACCT_SERVICE_REGEX = Pattern.compile("^/ws/.+?/(.+?)/.+$");
	
	/* (non-Javadoc)
	 * @see play.mvc.Action#call(play.mvc.Http.Context)
	 */
	@Override
	public Result call(Context ctx) throws Throwable {
		String path = ctx.request().path();
		String authToken = "";
		if ( ctx.request().cookies() != null
				&& ctx.request().cookies().get("authToken") != null ) {
			 authToken = ctx.request().cookies().get("authToken").value(); 
		}
		
		String username = "";
		Matcher m = ACCT_SERVICE_REGEX.matcher(path);
		if ( m.matches() ) {
			username = m.group(1);
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
