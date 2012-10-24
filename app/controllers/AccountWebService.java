package controllers;

import global.GlobalContext;

import org.jcheng.service.account.AccountService;
import org.jcheng.service.account.AuthorizationService;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import actions.AccountAuthAction;

import com.google.common.base.Strings;

/**
 * Provides a JSON web service for managing accounts.
 * 
 * @author jcheng
 *
 */
public class AccountWebService extends Controller {
	
	private static AccountService accountService = (AccountService) GlobalContext.getApplicationContext().getBean("accountService");
	private static AuthorizationService authorizationService = (AuthorizationService) GlobalContext.getApplicationContext().getBean("authorizationService");
	
	public static Result doLogin(String username, String pwHash) {
		boolean isValid = accountService.isLoginValid(username, pwHash);
		WSResult result;
		if ( isValid ) {
			String authToken = authorizationService.getToken(username);
			result = new WSResult(true, authToken);
		} else {
			result = new WSResult(false, "login failed");
		}
		return ok(Json.toJson(result));
	}
	
	@With(AccountAuthAction.class)
	public static Result getLuckyColor(String username) {
		String luckyColor = accountService.getLuckyColor(username);
		WSResult result;
		if ( Strings.isNullOrEmpty(luckyColor) ) {
			result = new WSResult(false, "invalid request");
		} else {
			result = new WSResult(true, luckyColor);
		}
		return ok(Json.toJson(result));
	}

}
