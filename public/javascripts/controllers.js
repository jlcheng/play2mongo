'use strict';

/* Controllers */

function LoginCtrl($scope, $location, Auth, AccountModel) {
	$.removeCookie('authToken', {path:'/'});

	$scope.username = 'jcheng';
	$scope.password = 'password';
	$scope.loginMesage = '';

	$scope.doLogin = function(username, password) {
		if ( username && password ) {
			Auth.get({"username": username, "pwHash": password}, function(e) {
				var authToken = e.messages[0];
				$.cookie('authToken', authToken, { expires: 365, path: '/' });
				AccountModel.username = username;
				$location.path('/showLuckyColor');
			}, function(e) {
				$scope.loginMessage = 'Login failed';
			});
		} else {
			$scope.loginMessage = 'Please enter a username and password';
		}
	}

	$scope.doCreate = function(username, password) {
		Auth.create({"username": username, "pwHash": password}, function(e) {
			$scope.loginMessage = 'Account created. You can now log in.';
		});
	}
}

LoginCtrl.$inject = ['$scope', '$location', 'Auth', 'AccountModel'];

function ShowColorCtrl($scope, $location, Account, AccountModel) {
	$scope.user = AccountModel
	if ( !AccountModel.username  ) {
		$location.path('/login');
	} else {
		Account.get({"username": AccountModel.username}, function(e) {
			var luckyColor = e.messages[0];
			AccountModel.luckyColor = luckyColor;
		});
	}

	$scope.doRemoveAll = function() {
		console.log(AccountModel.username);
		Account.removeAll({username: AccountModel.username}, {},  function(e) {
			$location.path('/login');
		});
	}
}

ShowColorCtrl.$inject = ['$scope', '$location', 'Account', 'AccountModel']
