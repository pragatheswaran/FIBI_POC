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
var core_1 = require('@angular/core');
var Observable_1 = require('rxjs/Observable');
require('rxjs/Rx');
require('rxjs/add/operator/map');
require('rxjs/add/operator/catch');
var http_1 = require('@angular/http');
var UserService = (function () {
    function UserService(http) {
        var _this = this;
        this.http = http;
        this.users_url = "users";
        this.users_loginurl = "login";
        this.users_send_otpurl = "users/validateAndSendOTP";
        this.users_pwd_reseturl = "users/resetUserPassword";
        this.GetAll = function () {
            return _this.http.get(_this.users_url)
                .map(function (response) { return response.json(); });
            //.catch(this.handleError());
        };
        this.http = http;
    }
    UserService.prototype.createUser = function (body) {
        var bodyString = JSON.stringify(body); // Stringify payload
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        var options = new http_1.RequestOptions({ headers: headers }); // Create a request option
        return this.http.post(this.users_url, body, options) // ...using post request
            .map(function (res) { return res; })
            .catch(function (error) { return Observable_1.Observable.throw(error.json().error || 'Server error'); }); //...errors if any
    };
    UserService.prototype.authenticateUser = function (user) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); // ... Set content type to url concoded
        var options = new http_1.RequestOptions({ headers: headers }); // Create a request option
        var username = user.email;
        var password = user.password;
        var body = new http_1.URLSearchParams();
        body.set('username', username);
        body.set('password', password);
        return this.http.post(this.users_loginurl, body, options)
            .map(function (res) { return res; })
            .catch(function (error) { return Observable_1.Observable.throw(error.json().error || 'Server error'); }); //...errors if any
    };
    UserService.prototype.validateAndsendOTP = function (user) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        var username = user.email;
        var urlSearchParams = new http_1.URLSearchParams();
        urlSearchParams.set('emailId', username);
        var options = new http_1.RequestOptions({ search: urlSearchParams }); // Create a request option
        var body = "";
        return this.http.post(this.users_send_otpurl, body, options) // ...using post request
            .map(function (res) { return res; })
            .catch(function (error) { return Observable_1.Observable.throw(error.json().error || 'Server error'); }); //...errors if any
    };
    UserService.prototype.resetUserPassword = function (user) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        var otp = user.otp;
        var password = user.password;
        var urlSearchParams = new http_1.URLSearchParams();
        urlSearchParams.set('otp', otp);
        urlSearchParams.set('password', password);
        var options = new http_1.RequestOptions({ search: urlSearchParams }); // Create a request option
        var body = "";
        return this.http.post(this.users_pwd_reseturl, body, options) // ...using post request
            .map(function (res) { return res; })
            .catch(function (error) { return Observable_1.Observable.throw(error.json().error || 'Server error'); }); //...errors if any
    };
    UserService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], UserService);
    return UserService;
}());
exports.UserService = UserService;
//# sourceMappingURL=UserService.js.map