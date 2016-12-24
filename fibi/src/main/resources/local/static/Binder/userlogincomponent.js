"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var user_js_1 = require("../Model/user.js");
var CountryService_js_1 = require('../Service/CountryService.js');
var UserService_js_1 = require('../Service/UserService.js');
var userlogincomponent = (function () {
    function userlogincomponent(_countryService, _userService) {
        this._countryService = _countryService;
        this._userService = _userService;
        this.userObj = new user_js_1.user();
        this.signup_success = false;
        this.signup_error = false;
        this.signin_success = false;
        this.signin_error = false;
        this.otpEmailDetails = true;
        this.passwordResetDetails = false;
        this.invalid_email = false;
        this.otp_success = false;
        this.invalid_otp = false;
        this.reset_success = false;
        this._countryService = _countryService;
        this._userService = _userService;
    }
    userlogincomponent.prototype.ngOnInit = function () {
        this.getCountries();
        //this.getCitiesByCountry(this.userObj.country);
    };
    userlogincomponent.prototype.getCountries = function () {
        var _this = this;
        this._countryService
            .GetAll()
            .subscribe(function (data) { return _this.countries = data; }, function (error) { return console.log(error); }, function () { return console.log('Get all Items complete'); });
    };
    userlogincomponent.prototype.getCitiesByCountry = function (country) {
        var _this = this;
        this._countryService
            .GetCitiesByCountry(country)
            .subscribe(function (data) { return _this.cities = data.cities; }, function (error) { return console.log(error); }, function () { return console.log('Cities fetched successfully'); });
    };
    userlogincomponent.prototype.createUser = function (user) {
        var _this = this;
        this.signup_success = false;
        this.signup_error = false;
        this._userService
            .createUser(user).subscribe(function (data) {
            var response = data;
            if (response.status == 200) {
                _this.signup_success = true;
            }
            else {
                _this.signup_error = true;
                throw new Error('User creation failed ' + response.status);
            }
            console.log(_this.data);
        }, function (error) {
            console.log(error);
            _this.signup_error = true;
        }, function () {
            console.log('User created successfully');
        });
    };
    userlogincomponent.prototype.authenticateUser = function (user) {
        var _this = this;
        this.signin_success = false;
        this.signin_error = false;
        this._userService
            .authenticateUser(user).subscribe(function (data) {
            var response = data;
            if (response.status == 200) {
                _this.signin_success = true;
                window.location.href = '/fibi/userhome.html';
            }
            else {
                _this.signin_error = true;
                throw new Error('User authentication failed ' + response.status);
            }
            console.log(_this.data);
        }, function (error) {
            console.log(error);
            _this.signin_error = true;
        }, function () {
            console.log('User authenticated successfully');
        });
    };
    userlogincomponent.prototype._validateAndsendOTP = function (user) {
        var _this = this;
        this.invalid_email = false;
        this.otp_success = false;
        this._userService
            .validateAndsendOTP(user).subscribe(function (data) {
            var response = data;
            if (response.status == 200) {
                _this.otp_success = true;
                _this.passwordResetDetails = true;
                _this.otpEmailDetails = false;
            }
            else {
                _this.invalid_email = true;
                throw new Error('Unable to send OTP' + response.status);
            }
            console.log(_this.data);
        }, function (error) {
            console.log(error);
            _this.invalid_email = true;
        }, function () {
            console.log('OTP sent successfully');
        });
    };
    userlogincomponent.prototype._resetUserPassword = function (user) {
        var _this = this;
        this.invalid_otp = false;
        this.reset_success = false;
        this._userService
            .resetUserPassword(user).subscribe(function (data) {
            var response = data;
            if (response.status == 200) {
                _this.reset_success = true;
            }
            else {
                _this.invalid_otp = true;
                throw new Error('OTP is invalid' + response.status);
            }
            console.log(_this.data);
        }, function (error) {
            console.log(error);
            _this.invalid_otp = true;
        }, function () {
            console.log('Password has been reset successfully!');
        });
    };
    userlogincomponent.prototype.onCountryChange = function (event) {
        this.getCitiesByCountry(this.userObj.country);
    };
    userlogincomponent.prototype.login = function (event) {
        this.authenticateUser(this.userObj);
    };
    userlogincomponent.prototype.signup = function (event) {
        this.createUser(this.userObj);
    };
    userlogincomponent.prototype.validateAndSendOTP = function (event) {
        this._validateAndsendOTP(this.userObj);
    };
    userlogincomponent.prototype.resetUserPassword = function (event) {
        this._resetUserPassword(this.userObj);
    };
    userlogincomponent.prototype.closeModal = function (event) {
        window.location.reload();
    };
    userlogincomponent = __decorate([
        core_1.Component({
            selector: 'login-ui',
            templateUrl: 'userlogin.html',
            providers: [CountryService_js_1.CountryService, UserService_js_1.UserService]
        }), 
        __metadata('design:paramtypes', [CountryService_js_1.CountryService, UserService_js_1.UserService])
    ], userlogincomponent);
    return userlogincomponent;
}());
exports.userlogincomponent = userlogincomponent;
//# sourceMappingURL=userlogincomponent.js.map