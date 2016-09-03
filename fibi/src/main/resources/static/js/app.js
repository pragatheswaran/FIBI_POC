
/*'use strict';
var app = angular.module('login_app',['ngCookies','ngProgress']);

app.controller('login_ctrl', login_ctrl);

app.config(['$httpProvider', function ($httpProvider) {
}]);


function login_ctrl($rootScope, $scope, $http, $cookies, $window, ngProgressFactory) {
	
	$scope.progressbar = ngProgressFactory.createInstance();
	
	$scope.login = function(credentials) {
			
		$scope.progressbar.start();
		
		if(angular.isUndefined($scope.credentials)) {
			 $scope.warningmsg = "Fill in your credentials";
			 $scope.progressbar.complete();
		 }
		 else {
		   var username = $scope.credentials.username;
	       var password = $scope.credentials.password;
	       $scope.warningmsg = "";
	    
           var postData = 'username=' + username + '&password=' + password;
        
           var request = $http({
             method: "post",
             headers: {
            	'Content-Type': 'application/x-www-form-urlencoded'
             },
             url: 'login',
             data: postData,
             cache: false
           });
        
           request.success(function(data,status) {
        	
            var parser = new DOMParser();
            var doc = parser.parseFromString(data, 'application/xhtml+xml');
            if(doc.getElementById('loginDiv')!=null) {
            	$scope.warningmsg = "Authentication failed";
            	$scope.progressbar.complete();
            }
            else {
            	$window.location.href = '/sponsor';
            	$scope.progressbar.complete();
            }
        })
        request.error(function(data, status, headers, config) {
        	$window.location.href = '/sponsor/login';
        	$scope.progressbar.complete();
        })
	  }
	}	
}
*/
angular.module('sample', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs: 'controller'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

		function($rootScope, $http, $location, $route, $scope) {
			
			var self = this;

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			self.credentials = {};
			self.login = function(credentials) {
				   var username = $scope.controller.credentials.username;
			       var password = $scope.controller.credentials.password;
			    
		           var postData = 'username=' + username + '&password=' + password;
		        
		           var request = $http({
		             method: "post",
		             headers: {
		            	'Content-Type': 'application/x-www-form-urlencoded'
		             },
		             url: 'login',
		             data: postData,
		             cache: false
		           });
		           
		           request.success(function(data,status) {
		        	   console.log("Login succeeded");
		        	   $location.path("/");
		        	   $rootScope.authenticated = true;
		           })
		           request.error(function(data, status, headers, config) {
		        	   console.log("Login failed");
		        	   $rootScope.authenticated = false;
		           })
			};

			self.logout = function() {
				$http.post('logout', {}).finally(function() {
					$rootScope.authenticated = false;
					$location.path("/");
				});
			}

		}).controller('home', function($http) {
	var self = this;
	$http.get('users/resource/').then(function(response) {
		self.greeting = response.data;
	})
});