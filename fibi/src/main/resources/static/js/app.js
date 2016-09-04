
var app = angular.module('sample', ['ngRoute', 'ngCookies','ngProgress']);

//for confirm password
var compareTo = function() {
    return {
      require: "ngModel",
      scope: {
        otherModelValue: "=compareTo"
      },
      link: function(scope, element, attributes, ngModel) {

        ngModel.$validators.compareTo = function(modelValue) {
          return modelValue == scope.otherModelValue;
        };

        scope.$watch("otherModelValue", function() {
          ngModel.$validate();
        });
      }
    };
  };
  
//config
app.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs: 'controller'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation',
		controllerAs: 'controller'
	}).when('/signup', {
		templateUrl : 'signup.html',
		controller : 'navigation',
		controllerAs: 'controller'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});

//directive
app.directive('compareTo', compareTo);

//controller
app.controller('navigation',

		function($rootScope, $http, $location, $route, $scope, ngProgressFactory) {
			
			var self = this;
			
			$scope.progressbar = ngProgressFactory.createInstance();
			
			/*$scope.countries = [
			                { id: 1, name: 'India' },
			                { id: 2, name: 'United States' },
			                { id: 3, name: 'Germany' }
			              ];*/
			
			$http.get('countries').success(function (data) {
		        $scope.countries = data;            
		    })
		    .error(function () {
		    	$rootScope.error = true;
		        //$scope.error = "An Error has occured while loading posts!";           
		    });
			
			$rootScope.error = false;

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			self.credentials = {};
			self.login = function(credentials) {
				   var username = $scope.controller.credentials.username;
			       var password = $scope.controller.credentials.password;
			    
			       $scope.progressbar.start();
			       
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
		        	   $rootScope.signupSuccess = false;
		        	   $location.path("/");
		        	   $rootScope.authenticated = true;
				       $scope.progressbar.complete();
		           })
		           request.error(function(data, status, headers, config) {
		        	   console.log("Login failed");
		        	   $rootScope.authenticated = false;
		        	   $rootScope.signupSuccess = false;
		        	   $rootScope.error = true;
				       $scope.progressbar.complete();
		           })
			};
			
			self.signup = function(signup) {
				   var email = $scope.controller.signup.email;
			       var password = $scope.controller.signup.password;
			       var firstName = $scope.controller.signup.firstName;
			       var lastName = $scope.controller.signup.lastName;
			       var country = $scope.controller.signup.country.name;
			       
			       $scope.progressbar.start();
			    		        
		           var request = $http({
		             method: "post",
		             headers: {
			            	'Content-Type': 'application/json'
			            },
		             url: 'users',
		             data: {
			             email: email,
			             firstName: firstName,
			             lastName: lastName,
			             country: country,
			             password: password
			         },
		             cache: false
		           });
		           
		           request.success(function(data,status) {
		        	   console.log("Signed up successfully");
		        	   $rootScope.signupSuccess = true;
		        	   $scope.progressbar.complete();
		        	   $location.path("/login");
		           })
		           request.error(function(data, status, headers, config) {
		        	   console.log("Sign up failed");
		        	   $scope.progressbar.complete();
		        	   $rootScope.error = true;
		           })
			};

			self.logout = function() {
				$http.post('logout', {}).finally(function() {
					$rootScope.authenticated = false;
					$location.path("/");
					 $rootScope.error = false;
				});
			}
		});
		
  app.controller('home', function($http) {
	var self = this;
	$http.get('users/resource/').then(function(response) {
		self.greeting = response.data;
	})
   });