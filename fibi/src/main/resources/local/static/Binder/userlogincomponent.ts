import { Component } from "@angular/core";
import { user } from "../Model/user.js";
import { country } from "../Model/country.js";
import {CountryService} from '../Service/CountryService.js';
import {UserService} from '../Service/UserService.js';
import { Observable } from 'rxjs/Observable';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

@Component({
    selector: 'login-ui',
    templateUrl: 'userlogin.html',
    providers: [CountryService, UserService]
})
export class userlogincomponent {

    constructor(private _countryService: CountryService, private _userService: UserService) {
        this._countryService = _countryService;
        this._userService = _userService;
    }

    ngOnInit() {
        this.getCountries();
        //this.getCitiesByCountry(this.userObj.country);
    }

    userObj: user = new user();
    public countries: country[];
    public cities: string[];
    public data: Response;
    public signup_success = false;
    public signup_error = false;
    public signin_success = false;
    public signin_error = false;
    public otpEmailDetails = true;
    public passwordResetDetails = false;
    public invalid_email = false;
    public otp_success = false;
    public invalid_otp = false;
    public reset_success = false;
    
    getCountries() {
        this._countryService
            .GetAll()
            .subscribe((data: country[]) => this.countries = data,
            error => console.log(error),
            () => console.log('Get all Items complete'));
    }

    getCitiesByCountry(country) {
        this._countryService
            .GetCitiesByCountry(country)
            .subscribe((data: country) => this.cities = data.cities,
            error => console.log(error),
            () => console.log('Cities fetched successfully'));
    }
  
   createUser(user) {
       this.signup_success = false;
       this.signup_error = false;
       this._userService
            .createUser(user).subscribe(
              (data: Response) => {
               let response = data;
               if(response.status == 200) {
                   this.signup_success = true;
               } else {
                  this.signup_error = true;
                  throw new Error('User creation failed ' + response.status);
               }  
               console.log(this.data);
              },
              error => {
               console.log(error);
               this.signup_error = true;
              },
              ()=> {
               console.log('User created successfully');    
              })
    } 
   
    authenticateUser(user) {
       this.signin_success = false;
       this.signin_error = false;
       this._userService
            .authenticateUser(user).subscribe(
              (data: Response) => {
               let response = data;
               if(response.status == 200) {
                   this.signin_success = true;
                   window.location.href = '/fibi/userhome.html'; 
               } else {
                  this.signin_error = true;
                  throw new Error('User authentication failed ' + response.status);
               }  
               console.log(this.data);
              },
              error => {
               console.log(error);
               this.signin_error = true;
              },
              ()=> {
               console.log('User authenticated successfully');
              })
    }  
  
    _validateAndsendOTP(user) {
       this.invalid_email = false;
       this.otp_success = false;
       this._userService
            .validateAndsendOTP(user).subscribe(
              (data: Response) => {
               let response = data;
               if(response.status == 200) {
                   this.otp_success = true;
                   this.passwordResetDetails = true;
                   this.otpEmailDetails = false;
               } else {
                  this.invalid_email = true;
                  throw new Error('Unable to send OTP' + response.status);
               }  
               console.log(this.data);
              },
              error => {
               console.log(error);
               this.invalid_email = true;
              },
              ()=> {
               console.log('OTP sent successfully');    
              })
    }
  
    _resetUserPassword(user) {
       this.invalid_otp = false;
       this.reset_success = false;
       this._userService
            .resetUserPassword(user).subscribe(
              (data: Response) => {
               let response = data;
               if(response.status == 200) {
                   this.reset_success = true;
               } else {
                  this.invalid_otp = true;
                  throw new Error('OTP is invalid' + response.status);
               }  
               console.log(this.data);
              },
              error => {
               console.log(error);
               this.invalid_otp = true;
              },
              ()=> {
               console.log('Password has been reset successfully!');    
              })
    }
    
    onCountryChange(event) {
        this.getCitiesByCountry(this.userObj.country);
    }
  
    login(event) { 
      this.authenticateUser(this.userObj);
    }

    signup(event) {
      this.createUser(this.userObj);
    }
  
    validateAndSendOTP(event) {
      this._validateAndsendOTP(this.userObj);
    }
   
    resetUserPassword(event) {
      this._resetUserPassword(this.userObj);
    }
  
    closeModal(event) {
      window.location.reload();  
    }
}