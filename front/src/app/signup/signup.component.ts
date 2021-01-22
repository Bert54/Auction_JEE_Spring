/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, Validators} from '@angular/forms';
import { AuthenticationService } from '../shared/services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../shared/interfaces/User';
import {MatchValidators} from '../shared/validators/MatchValidators';
import {PasswordsStateMatcher} from '../shared/validators/PasswordsStateMatcher';
import {AdressValidator} from '../shared/validators/AdressValidator';
import {AdressStateMatcher} from '../shared/validators/AdressStateMatcher';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  private readonly _form: FormGroup;
  private _hasError = false;
  private _errorContent;
  private _hide = true;
  private _hideC = true;
  public _matcher: PasswordsStateMatcher;
  public _registrationSuccess: boolean;
  public _addressMatcher: AdressStateMatcher;

  constructor(private _authService: AuthenticationService, private _router: Router) {
    this._form = this.buildForm();
  }

  ngOnInit(): void {
    this._registrationSuccess = false;
    this._matcher = new PasswordsStateMatcher();
    this._addressMatcher = new AdressStateMatcher();
  }

  get form(): FormGroup {
    return this._form;
  }

  private buildForm(): FormGroup {
    return new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      cpassword: new FormControl(''),
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      street: new FormControl(''),
      city: new FormControl(''),
      postcode: new FormControl('',  Validators.pattern('(^[0-9]{5}|$)')),
      houseNumber: new FormControl(''),
    },
      {
        validators: Validators.compose([MatchValidators.mustMatch, AdressValidator.adressComplete])
      });
  }

  get hasError(): boolean {
    return this._hasError;
  }

  get errorContent(): string {
    return this._errorContent;
  }

  get hide(): boolean {
    return this._hide;
  }

  set hide(value: boolean) {
    this._hide = value;
  }

  get hideC(): boolean {
    return this._hideC;
  }

  set hideC(value: boolean) {
    this._hideC = value;
  }

  get registrationSuccess(): boolean  {
    return this._registrationSuccess;
  }


  public signup(formValues: any): void {
    if (formValues.username && formValues.password) {
        const user: User = {
          username: formValues.username,
          password: formValues.password,
          firstName: formValues.firstName,
          lastName: formValues.lastName,
          street: formValues.street ? formValues.street : null,
          city: formValues.city ? formValues.city : null,
          postcode: formValues.postcode ? formValues.postcode : 0,
          houseNumber: formValues.houseNumber ? formValues.houseNumber : 0
        };
        this._authService.createUser(user)
          .subscribe(
            _ => {
              this._registrationSuccess = true;
            },
            err => {
              this._hasError = true;
              if (err.status === 409) {
                this._errorContent = 'User already exists';
                console.log(err);
              } else {
                this._errorContent = err.status + ' : ' + err.message;
                console.log(err);
              }
            }
          );
      }
  }

}
