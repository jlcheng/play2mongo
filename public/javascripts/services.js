'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.


angular.module('myApp.services', ['ngResource']).
    factory('Account', function($resource){
    	return $resource('/ws/v1/doLogin', {}, {
    		get: {method:'GET', params:{phoneId:'phones'}, isArray:false}
    	});
    }).
    value('version', '0.1');
