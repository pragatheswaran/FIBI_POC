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
require('rxjs/Rx');
require('rxjs/add/operator/map');
require('rxjs/add/operator/catch');
var http_1 = require('@angular/http');
var CountryService = (function () {
    function CountryService(http) {
        var _this = this;
        this.http = http;
        this.countries_url = "countries";
        this.GetAll = function () {
            return _this.http.get(_this.countries_url)
                .map(function (response) { return response.json(); });
            //.catch(this.handleError);
        };
        this.GetCitiesByCountry = function (country) {
            return _this.http.get(_this.countries_url + "/" + country)
                .map(function (response) { return response.json(); });
            //.catch(this.handleError);
        };
        this.http = http;
    }
    CountryService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], CountryService);
    return CountryService;
}());
exports.CountryService = CountryService;
//# sourceMappingURL=CountryService.js.map