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
var platform_browser_1 = require('@angular/platform-browser');
var userlogincomponent_1 = require('./userlogincomponent');
var forms_1 = require('@angular/forms');
var http_1 = require('@angular/http');
var equal_validator_directive_js_1 = require('../Validator/equal-validator.directive.js');
var usermodule = (function () {
    function usermodule() {
    }
    usermodule = __decorate([
        core_1.NgModule({
            imports: [platform_browser_1.BrowserModule, forms_1.FormsModule, http_1.HttpModule],
            declarations: [userlogincomponent_1.userlogincomponent, equal_validator_directive_js_1.EqualValidator],
            bootstrap: [userlogincomponent_1.userlogincomponent]
        }), 
        __metadata('design:paramtypes', [])
    ], usermodule);
    return usermodule;
}());
exports.usermodule = usermodule;
//# sourceMappingURL=usermodule.js.map