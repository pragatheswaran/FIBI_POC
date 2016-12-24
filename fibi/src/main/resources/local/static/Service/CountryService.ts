import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { country } from "../Model/country.js";
import { Http, Response, Headers } from '@angular/http';

@Injectable()
export class CountryService {

    private countries_url = "countries";
 
    constructor(private http: Http) {
        this.http = http;
    }

    public GetAll = (): Observable<country[]> => {
        return this.http.get(this.countries_url)
            .map((response: Response) => <country[]>response.json())
            //.catch(this.handleError);
    }

    public GetCitiesByCountry = (country): Observable<country> => {
        return this.http.get(this.countries_url +"/"+ country)
            .map((response: Response) => <country>response.json())
        //.catch(this.handleError);
    }
}