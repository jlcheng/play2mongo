'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('myApp.services', ['ngResource']).
    factory('Auth', function($resource){
    	return $resource('/ws/v1/auth/:action', {}, {
    		get:    {method:'GET',  params: {action: 'doLogin'},  isArray:false},
    		create: {method:'POST', params: {action: 'doCreate'}, isArray:false}
    	});
    }).
    factory('Account', function($resource){
    	return $resource('/ws/v1/:username/:action', {}, {
    		get:       {method:'GET', params: {action: 'luckyColor'}, isArray:false},
    		removeAll: {method:'PUT', params: {action: 'removeAll'}, isArray:false}
    	});
    }). 
    factory('AccountModel', function() {
    	var accountModel = {
    		username: ''
    	};
    	return accountModel;
    }).
    value('version', '0.1');
