
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
	}).when('/travel', {
		templateUrl : 'travel.html',
		controller : 'travel',
		controllerAs: 'controller'
	}).when('/searchTravels', {
		templateUrl : 'SearchTravels.html',
		controller : 'travel',
		controllerAs: 'controller'
	}).when('/travelDetails', {
		templateUrl : 'TravelDetails.html',
		controller : 'travel',
		controllerAs: 'controller'
	})
	.otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});

//directive
app.directive('compareTo', compareTo);

//controller
app.controller('navigation',

		function($rootScope, $http, $location, $route, $scope, ngProgressFactory, $window) {
			
			var self = this;
			
			$scope.progressbar = ngProgressFactory.createInstance();
			
			$scope.redirectToHome = function(){
				  $window.location = "#/";
			}
						
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
		        	   $location.path("/travel");
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
  
  app.controller('travel', function($rootScope, $http, $location, $route, $scope, ngProgressFactory, $window, $filter) {
		var self = this;
		
		$scope.progressbar = ngProgressFactory.createInstance();
		
		$rootScope.saveSuccess = false;
		 $rootScope.searchSuccess = false;

		$http.get('users/resource/').then(function(response) {
			self.greeting = response.data;
		})
		
		$scope.redirectToTravelDetails = function(){
			  $window.location = "#/travelDetails";
		}
		
		$scope.redirectToSearchTravel = function(){
			  $window.location = "#/searchTravels";
	    }
		
		self.saveTravelDetails = function(travelDetails) {
			   var from = $scope.controller.travelDetails.from;
		       var to = $scope.controller.travelDetails.to;
		       var datetime = $scope.controller.travelDetails.datetime;
		       var flightNo = $scope.controller.travelDetails.flightNo;
		       var weight = $scope.controller.travelDetails.weight;
		       
		       var datetime = $filter('date')(new Date(datetime),'yyyy-MM-dd HH:mm');
		       		       
		       $scope.progressbar.start();
		    		        
	           var request = $http({
	             method: "post",
	             headers: {
		            	'Content-Type': 'application/json'
		            },
	             url: 'travels',
	             data: {
	            	 departureCity: from,
	            	 destinationCity: to,
	            	 startDate: datetime,
	            	 flightNo: flightNo,
		             weight: weight
		         },
	             cache: false
	           });
	           
	           request.success(function(data,status) {
	        	   console.log("Travel details saved successfully");
	        	   $rootScope.saveSuccess = true;
	        	   $scope.progressbar.complete();
	        	   //$location.path("/travel");
	           })
	           request.error(function(data, status, headers, config) {
	        	   console.log("Unable to save travel details");
	        	   $rootScope.saveSuccess = false;
	        	   $scope.progressbar.complete();
	        	   //$rootScope.error = true;
	           })
		}
		
		self.searchTravellers = function(searchDetails) {
			   var from = $scope.controller.searchDetails.from;
		       var to = $scope.controller.searchDetails.to;
		       var startDate = $scope.controller.searchDetails.startDate;
		       var endDate = $scope.controller.searchDetails.endDate;
		       
		       var startDate = $filter('date')(new Date(startDate),'yyyy-MM-dd HH:mm');
		       var endDate = $filter('date')(new Date(endDate),'yyyy-MM-dd HH:mm');

		       $scope.progressbar.start();
		    		        
	           var request = $http({
	             method: "GET",
	             headers: {
		            	'Content-Type': 'application/json'
		            },
	             url: 'travels/search',
	             params: {
	            	 departureCity: from,
	            	 destinationCity: to,
	            	 startDate: startDate,
	            	 endDate: endDate
		         },
	             cache: false
	           });
	           
	           request.success(function(data,status) {
	        	   console.log("Search success");
	        	   self.searchResult = data;
	        	   $rootScope.searchSuccess = true;
	        	   $scope.progressbar.complete();
	        	   //$location.path("/travel");
	           })
	           request.error(function(data, status, headers, config) {
	        	   console.log("Search failure");
	        	   $rootScope.searchSuccess = false;
	        	   $scope.progressbar.complete();
	        	   //$rootScope.error = true;
	           })
		}
 
	   });