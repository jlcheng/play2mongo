'use strict';

/* Controllers */


function MyCtrl1($scope, $routeParams, Account) {
	
	Account.get({username: 'abcd', pwHash: 'asd'}, function(e) {
		console.log(e);
	});

	$scope.doLogin = function(username, password) {

		if ( username && password ) {
			alert('Login with username = ' + username + ' and password = ' + password);
		} else {
			alert('Please enter a username and password')
		}
	}
}


function MyCtrl2() {
}
MyCtrl2.$inject = [];
