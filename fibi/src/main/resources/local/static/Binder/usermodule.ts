import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { userlogincomponent } from './userlogincomponent';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { EqualValidator } from '../Validator/equal-validator.directive.js'; 
@NgModule({
    imports: [BrowserModule, FormsModule, HttpModule],
    declarations: [userlogincomponent, EqualValidator],
    bootstrap: [userlogincomponent]
})
export class usermodule { }
