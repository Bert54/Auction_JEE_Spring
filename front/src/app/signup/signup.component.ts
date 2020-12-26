/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, Validators} from '@angular/forms';
import { AuthenticationService } from '../shared/services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../shared/interfaces/User';
import {MatchValidators} from '../shared/validators/MatchValidators';
import {PasswordsStateMatcher} from '../shared/validators/PasswordsStateMatcher';

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

  constructor(private _authService: AuthenticationService, private _router: Router) {
    this._form = this.buildForm();
  }

  ngOnInit(): void {
    this._registrationSuccess = false;
    this._matcher = new PasswordsStateMatcher();
  }

  get form(): FormGroup {
    return this._form;
  }

  private buildForm(): FormGroup {
    return new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      cpassword: new FormControl('')
    },
      {
        validators: MatchValidators.mustMatch
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
        password: formValues.password
      };
      this._authService.createUser(user)
        .subscribe(
          _ => {
            this._registrationSuccess = true;
          },
          err => {
            this._hasError = true;
            if (err.status === 409){
              this._errorContent = 'User already exists';
              console.log(err);
            } else{
              this._errorContent = err.status + ' : ' + err.message;
            }
          }
        );
    }
  }

}
