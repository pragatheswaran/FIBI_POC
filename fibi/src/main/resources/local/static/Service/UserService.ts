import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { user } from "../Model/user.js";
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';

@Injectable()
export class UserService {

    private users_url = "users";
    private users_loginurl = "login";
    private users_send_otpurl = "users/validateAndSendOTP";
    private users_pwd_reseturl = "users/resetUserPassword";
  
    constructor(private http: Http) {
        this.http = http;
    }

    public GetAll = (): Observable<user[]> => {
        return this.http.get(this.users_url)
            .map((response: Response) => <user[]>response.json())
            //.catch(this.handleError());
    }
    
   public createUser (body: Object): Observable<Response> {
        let bodyString = JSON.stringify(body); // Stringify payload
        let headers      = new Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        let options       = new RequestOptions({ headers: headers }); // Create a request option

        return this.http.post(this.users_url, body, options) // ...using post request
                         .map((res:Response) => res)
                         .catch<Response>((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
    }
   
    public authenticateUser (user: user): Observable<Response> {
        let headers      = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' }); // ... Set content type to url concoded
        let options       = new RequestOptions({ headers: headers }); // Create a request option
        let username = user.email;
        let password = user.password;
              
        let body = new URLSearchParams();
        body.set('username', username);
        body.set('password', password);
        
        return this.http.post(this.users_loginurl, body, options)
                         .map((res:Response) => res)
                         .catch<Response>((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
    }
 
    public validateAndsendOTP (user: user): Observable<Response> {
        let headers      = new Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        
        let username = user.email;
      
        let urlSearchParams  = new URLSearchParams();
        urlSearchParams.set('emailId', username);
      
        let options = new RequestOptions({search: urlSearchParams}); // Create a request option
        let body = "";
        
        return this.http.post(this.users_send_otpurl, body, options) // ...using post request
                         .map((res:Response) => res)
                         .catch<Response>((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
    }
  
     public resetUserPassword (user: user): Observable<Response> {
        let headers      = new Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        
        let otp = user.otp;
        let password = user.password;
      
        let urlSearchParams  = new URLSearchParams();
        urlSearchParams.set('otp', otp);
        urlSearchParams.set('password', password);
      
        let options = new RequestOptions({search: urlSearchParams}); // Create a request option
        let body = "";
        
        return this.http.post(this.users_pwd_reseturl, body, options) // ...using post request
                         .map((res:Response) => res)
                         .catch<Response>((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
    }
  }