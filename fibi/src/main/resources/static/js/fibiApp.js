var app = angular.module('fibiApp', ['720kb.datepicker','ngProgress', 'ngAutocomplete']);


var map;
var infowindow;
var infowindow2;
var pos;

angular.isUndefinedOrNull = function(val) {
    return angular.isUndefined(val) || val === null 
}


function initDefaultMap() {
	map = new google.maps.Map(document.getElementById('map'), {
	    center: {lat: -34.397, lng: 150.644},
	    zoom: 10
	   });
	//infoWindow = new google.maps.InfoWindow({map: map});
}


function currentLocationMap() {
	
  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: -34.397, lng: 150.644},
    zoom: 10
   });
  infoWindow = new google.maps.InfoWindow({map: map});
  
  // HTML5 geolocation.
  if (navigator.geolocation) {
   navigator.geolocation.getCurrentPosition(function(position) {
      pos = {
         lat: position.coords.latitude,
         lng: position.coords.longitude
     };

     infoWindow.setPosition(pos);
     infoWindow.setContent('Location found.');
     map.setCenter(pos);
     
     infowindow2 = new google.maps.InfoWindow();
     var service = new google.maps.places.PlacesService(map);
     service.nearbySearch({
       location: pos,
       radius: 10000,
       type: ['restaurant']
     }, callback);
     
  }, function() {
     handleLocationError(true, infoWindow, map.getCenter());
  });
  } else {
   // Browser doesn't support Geolocation
   handleLocationError(false, infoWindow, map.getCenter());
  }
}

    function callback(results, status) {
	 google.maps.event.trigger(map, 'resize');
	 map.setCenter(pos);
	
	  if (status === google.maps.places.PlacesServiceStatus.OK) {
	    for (var i = 0; i < results.length; i++) {
	      createMarker(results[i]);
	    }
	  }
    }

	function createMarker(place) {
	  var placeLoc = place.geometry.location;
	  var marker = new google.maps.Marker({
	    map: map,
	    position: place.geometry.location
	  });

	  google.maps.event.addListener(marker, 'click', function() {
	    infowindow2.setContent(place.name);
	    infowindow2.open(map, this);
	  });
	}



function handleLocationError(browserHasGeolocation, infoWindow, pos) {
 infoWindow.setPosition(pos);
 infoWindow.setContent(browserHasGeolocation ?
                       'Error: The Geolocation service failed. Please use non-chrome browsers for now :)' :
                       'Error: Your browser doesn\'t support geolocation.');
}

  function initMap(city) {
	
  var location = cityLocationMap[city];
  
  var geoLocs = location.split(',');
    
  var loc = {lat: parseInt(geoLocs[0]), lng: parseInt(geoLocs[1])};
  
  var pyrmont = {lat: 13.0827, lng: 80.2707};
  
  map = new google.maps.Map(document.getElementById('map'), {
    center: loc,
    zoom: 12
  });
  
  infowindow = new google.maps.InfoWindow();
  var service = new google.maps.places.PlacesService(map);
  service.nearbySearch({
    location: loc,
    radius: 10000,
    type: ['restaurant']
  }, callback);
}
  
  function handleGeoLocation(loc) {
			    		  
	  map = new google.maps.Map(document.getElementById('map'), {
	    center: loc,
	    zoom: 14
	  });
	  
	  infowindow = new google.maps.InfoWindow();
	  var service = new google.maps.places.PlacesService(map);
	  service.nearbySearch({
	    location: loc,
	    radius: 10000,
	    type: ['restaurant']
	  }, callback);
	}
  

function callback(results, status) {
  if (status === google.maps.places.PlacesServiceStatus.OK) {
    for (var i = 0; i < results.length; i++) {
      createMarker(results[i]);
    }
   
    var lastCenter=map.getCenter(); 
    google.maps.event.trigger(map, 'resize');
    map.setCenter(lastCenter);
  }
}

function createMarker(place) {
  var placeLoc = place.geometry.location;
  var marker = new google.maps.Marker({
    map: map,
    position: place.geometry.location
  });
  
  google.maps.event.addListener(marker, 'click', function() {
    infowindow.setContent(place.name);
    infowindow.open(map, this);
  });
  
}

// testdata
var cityLocationMap= {
        'Stuttgart': '48.7758,9.1829',
        'Chennai': '13.0827,80.2707',
        'Coimbatore': '11.0168,76.9558',
        'New York': '40.7127840,-74.0059410',
        'Berlin': '52.5200,13.4050'
        }

// for confirm password
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
  
  app.config(['$httpProvider', function($httpProvider) {
	  $httpProvider.defaults.useXDomain = true;
      delete $httpProvider.defaults.headers.common['X-Requested-With'];
/*
 * $routeProvider.
 * 
 * when('/', { templateUrl: '/', controller: 'userHomeController' }).
 * when('/userhome', { templateUrl: '/userhome.htm', controller:
 * 'userHomeController' }). when('/userprofile', { templateUrl:
 * '/usermessages.htm', controller: 'userHomeController' }). when('/addStudent', {
 * templateUrl: 'addStudent.htm', controller: 'userHomeController' }).
 * otherwise({ redirectTo: '/userprofile' });
 * 
 */	}]);  

// config
/*
 * app.config(function($routeProvider, $httpProvider) { $routeProvider
 * .when('/', { templateUrl: '/', controller: 'homeController' })
 * .when('/userhome', { templateUrl: '/userhome.html', controller:
 * 'userHomeController' }) .when('/another', { templateUrl:
 * '/partials/template1.html', controller: 'ctrl1' }) .otherwise({ redirectTo:
 * '/' }); });
 */    
// directive
  app.directive('compareTo', compareTo);
  
  
// controller
 app.controller('userHomeController', function($http, $scope, $rootScope, $window,
			$location, ngProgressFactory, $filter) {

	 var self = this;
	
	 $scope.showcommunitymessageboard = false;
	 $scope.showhome = true;
	 $scope.showprofile = false;
	 $scope.showmessages = false;
	 $scope.showtravels = false;
	 $scope.showapartments = false;
	 $scope.showrestaurants = false;
	 $scope.showevents = false;
	 $scope.showuseditems = false;
	 $scope.showSearchTravel = false;
	 $scope.showImTravelling = false;
	 $scope.showmap = false;
	 
	 $scope.result1 = '';   
	 $scope.options1 = null;
	 $scope.details1 = '';

	 $scope.progressbar = ngProgressFactory.createInstance();
	 
	 // Search Restaurants
	 $scope.getRestaurants = function() {		 		 
		 if(angular.isUndefinedOrNull($scope.details1.geometry)) {
			//alert("Choose your city");
		 } else {
			$scope.progressbar.start();	
		    handleGeoLocation($scope.details1.geometry.location);
		    $scope.progressbar.complete();
		    $scope.showmap = true;
		 }		 
	}
	 
	 $http.get('users/resource/').then(function(response) {
			$scope.greeting = response.data;
		})
	
	 $scope.logout = function() {
			$http.post('logout', {}).finally(function() {
				$window.location = "/fibi/";
			});
	 }
	 $scope.scrollToBottom = function() {		 
		 $("#scrollBarDiv").animate({ scrollTop: 999999}, 2000);
	 }
	 	 
	 $scope.postMessage = function(code, userId, firstName) {
		 
			var message = $scope.controller.postMessage;
			var postedDate = $filter('date')(new Date(),'yyyy-MM-dd HH:mm');
			
	        var request = $http({
		             method: "post",
		             headers: {
			            	'Content-Type': 'application/json'
			            },
		             url: 'messageBoard',
		             data: {
		            	 postedDate: postedDate,
		            	 message: message,
		            	 communityCode: code,
		            	 user: {
		            		userId: userId, 
		            		firstName:firstName
		            	 }
			         },
		             cache: false
		           });
		           
		           request.success(function(data,status) {
		        	   console.log("Message posted successfully successfully");
		        	   $scope.controller.postMessage = "";
		        	   $scope.showCommunityMessageBoard();
		        	   $scope.scrollToBottom();
		           })
		           request.error(function(data, status, headers, config) {
		        	   console.log("Unable to post message");
		        	   $scope.controller.postMessage = "";
		        	   $scope.showCommunityMessageBoard();
		           })	
	 }
	
	 	  
	 $scope.showHome = function() {
		 $scope.showhome = true;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }
	 
	 $scope.showProfile = function() {
		 $scope.showhome = false;
		 $scope.showprofile = true;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }

	 $scope.showMessages = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = true;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }
	 
	 $scope.showCommunityMessageBoard = function() {
		  
		 var communityCode = $scope.greeting.community;
		 
	     var request = $http({
	          method: "GET",
	          url: 'messageBoard/community/'+communityCode,
	          cache: false
	      });
	        
	     request.success(function(data,status) {
	     	  console.log("Received messages");
	     	  $scope.messageBoardMessages = data;
	     	  $scope.scrollToBottom();
	     })
	     request.error(function(data, status, headers, config) {
	          console.log("Failed to receive messages");
	     })

		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = true;	
		 $scope.showmap = false;
	 }

	 
	 $scope.showTravels = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = true;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	  }	 
     
 
	 $scope.showApartments = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = true;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }

	 $scope.showRestaurants = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = true;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 //$scope.showmap = true;
		 //initDefaultMap();
		 // currentLocationMap();
		 // $scope.showmap = false;
	 }
	 
	 $scope.showEvents = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = true;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }

	 
	 $scope.showUseditems = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = true;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }
	 
	 
	 $scope.redirectToSearchTravel = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = true;
		 $scope.showImTravelling = false;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }
	 
	 $scope.redirectToTravelDetails = function() {
		 $scope.showhome = false;
		 $scope.showprofile = false;
		 $scope.showmessages = false;
		 $scope.showtravels = false;
		 $scope.showapartments = false;
		 $scope.showrestaurants = false;
		 $scope.showevents = false;
		 $scope.showuseditems = false;
		 $scope.showSearchTravel = false;
		 $scope.showImTravelling = true;
		 $scope.showcommunitymessageboard = false;
		 $scope.showmap = false;
	 }
	 	 
	 $scope.swapLocations = function() {
		 var from = $scope.controller.searchDetails.from;	   
         $scope.controller.searchDetails.from = $scope.controller.searchDetails.to;
         $scope.controller.searchDetails.to = from; 
	 }
	 	 
	 $scope.searchTravellers = function(searchDetails) {
		 
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
	     	  $scope.progressbar.complete();
	     	  $scope.searchResult = data;
	     	  // self.searchResult = data;
	       })
	       request.error(function(data, status, headers, config) {
	           console.log("Search failure");
	           $scope.progressbar.complete();
	        })
		}
	 
	 
		$scope.saveTravelDetails = function(travelDetails) {
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
	        	   alert("Travel details successfully");
	        	   // $rootScope.saveSuccess = true;
	        	   $scope.progressbar.complete();
	        	   // $location.path("/travel");
	           })
	           request.error(function(data, status, headers, config) {
	        	   console.log("Unable to save travel details");
	        	   // $rootScope.saveSuccess = false;
	        	   $scope.progressbar.complete();
	        	   // $rootScope.error = true;
	           })
		}

 });
	
 
// controller
app.controller('homeController', function($http, $scope, $rootScope, $window,
		$location, ngProgressFactory) {
	var self = this;
	
	$scope.progressbar = ngProgressFactory.createInstance();
	
	$scope.otpEmailDetails = true;

	$scope.closeModal = function() {
		$window.location.reload();		
	}
	
/*
 * $(function(){ // let all dom elements are loaded
 * $('#loginModal').on('hide.bs.modal', function (e) { alert('event fired') });
 * });
 */	
	// $('#loginModal').modal('show');
	
/*
 * $('#loginModal').on('hide',function(e){ if(!confirm('You want to close me?'))
 * e.preventDefault(); });
 */	
/*
 * $('#loginModal').on('hidden.bs.modal', function () { alert("test"); })
 * 
 * $('#loginModal').on('hidden', function () { alert("test"); })
 */	
	$http.get('countries/names').success(function (data) {
        $scope.countries = data;            
    })
    
    $scope.updateCities = function() {
		
		var country = $scope.controller.signup.country.name;
		
		$http.get('countries/'+country).success(function (data) {
	        $scope.cities = data.cities;            
	    })
	}
    
    $scope.ssoLogin = function() {
		// $window.location = "/fibi/userhome.html";
		$window.location = "fibi/login/facebook";
	}
    
	$scope.validateAndSendOTP = function(credentials) {
		var email = $scope.credentials.username;

		$scope.progressbar.start();
		
		$scope.otp_success = false;
		$scope.invalid_email = false;
		
		var request = $http({
			method : "post",
			headers : {
				'Content-Type' : 'application/json'
			},
			url : 'users/validateAndSendOTP',
			params: {
	         	 emailId: email,
		    },
			/*
			 * data : { email : email, },
			 */
			cache : false
		});

		request.success(function(data, status) {
			console.log("OTP sent successfully");
			$scope.otp_success = true;
			$scope.progressbar.complete();
			$scope.passwordResetDetails = true;
			$scope.otpEmailDetails = false;
		})
		request.error(function(data, status, headers, config) {
			console.log("Invalid email");
			$scope.progressbar.complete();
			$scope.invalid_email = true;
		})
		
		$scope.progressbar.complete();
	};
	
	$scope.resetUserPassword = function(credentials) {
		var otp = $scope.credentials.otp;
		var password = $scope.credentials.password;

		$scope.progressbar.start();
		
		$scope.reset_success = false;
		$scope.invalid_otp = false;
		
		var request = $http({
			method : "post",
			headers : {
				'Content-Type' : 'application/json'
			},
			url : 'users/resetUserPassword',
			params: {
	         	 otp: otp,
	         	 password: password
		    },
			cache : false
		});

		request.success(function(data, status) {
			console.log("Password updated successfully");
			$scope.reset_success = true;
			$scope.progressbar.complete();
		})
		request.error(function(data, status, headers, config) {
			console.log("Invalid OTP");
			$scope.progressbar.complete();
			$scope.invalid_otp = true;
		})
		
		$scope.progressbar.complete();
	};

	$scope.login = function(credentials) {
		var username = $scope.credentials.username;
		var password = $scope.credentials.password;

		$scope.progressbar.start();
		
		$scope.login_error = false;
		$scope.login_success = false;

		var postData = 'username=' + username + '&password=' + password;

		var request = $http({
			method : "post",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url : 'login',
			data : postData,
			cache : false
		});

		request.success(function(data, status) {
			console.log("Login succeeded");
			$scope.login_success = true;
			$window.location = "/fibi/userhome.html";
		})
		request.error(function(data, status, headers, config) {
			console.log("Login failed");
			$scope.login_error = true;
		})
		
		$scope.progressbar.complete();
	};

	$scope.signup = function(signup) {
		var email = $scope.controller.signup.email;
		var password = $scope.controller.signup.password;
		var firstName = $scope.controller.signup.firstName;
		var lastName = $scope.controller.signup.lastName;
		var country = $scope.controller.signup.country.name;
		var city = $scope.controller.signup.city;

		$scope.signup_error = false;
		$scope.signup_success = false;

		$scope.progressbar.start();

		var request = $http({
			method : "post",
			headers : {
				'Content-Type' : 'application/json'
			},
			url : 'users',
			data : {
				email : email,
				firstName : firstName,
				lastName : lastName,
				country : country,
				city : city,
				password : password
			},
			cache : false
		});

		request.success(function(data, status) {
			console.log("Signed up successfully");
			$scope.signup_success = true;
			$scope.progressbar.complete();
		})
		request.error(function(data, status, headers, config) {
			console.log(data.message);
			$scope.progressbar.complete();
			$scope.signup_error = true;
		})
	};	
});

$(document).ready(function () {

    // stick in the fixed 100% height behind the navbar but don't wrap it
    $('#slide-nav.navbar-inverse').after($('<div class="inverse" id="navbar-height-col"></div>'));
  
    $('#slide-nav.navbar-default').after($('<div id="navbar-height-col"></div>'));  
    
    // Enter your ids or classes
    var toggler = '.navbar-toggle';
    var pagewrapper = '#page-content';
    var navigationwrapper = '.navbar-header';
    var menuwidth = '100%'; // the menu inside the slide menu itself
    var slidewidth = '80%';
    var menuneg = '-100%';
    var slideneg = '-80%';
    
    $("#slide-nav").on("click", toggler, function (e) {

        var selected = $(this).hasClass('slide-active');

        $('#slidemenu').stop().animate({
            left: selected ? menuneg : '0px'
        });

        $('#navbar-height-col').stop().animate({
            left: selected ? slideneg : '0px'
        });

        $(pagewrapper).stop().animate({
            left: selected ? '0px' : slidewidth
        });

        $(navigationwrapper).stop().animate({
            left: selected ? '0px' : slidewidth
        });


        $(this).toggleClass('slide-active', !selected);
        $('#slidemenu').toggleClass('slide-active');


        $('#page-content, .navbar, body, .navbar-header').toggleClass('slide-active');

    });

    var selected = '#slidemenu, #page-content, body, .navbar, .navbar-header';

    $(window).on("resize", function () {

        if ($(window).width() > 767 && $('.navbar-toggle').is(':hidden')) {
            $(selected).removeClass('slide-active');
        }


    });
});
