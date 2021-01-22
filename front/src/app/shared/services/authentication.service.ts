/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Username } from '../interfaces/Username';
import { User } from '../interfaces/User';
import { HttpRequestsService } from './http-requests.service';
import {JWT} from '../interfaces/JWT';
import {Userinfo} from '../interfaces/Userinfo';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private _isLoggedIn: boolean;
  private _userName: string;

  constructor(private _http: HttpRequestsService) {
    this._userName = 'NOT_LOGGED_IN';
  }

  public login(user: User): Observable<any> {
    return this._http.login(user).pipe(
      tap(res => {
        this.setSession(res);
      })
    );
  }

  public fetchLoginUserName(): Observable<Username> {
    return this._http.fetchLoginUserName();
  }

  public createUser(user: User): Observable<any> {
    return this._http.createUser(user);
  }

  public refreshSessionStatus(): void {
    const idToken = localStorage.getItem('access_token');
    if (idToken) {
      this.fetchLoginUserName()
        .subscribe(res => {
        this._userName = res.username;
        this._isLoggedIn = true;
      });
    }
    else {
      this._isLoggedIn = false;
    }
  }

  private setSession(jwt: JWT): void {
    localStorage.setItem('access_token', jwt.token);
    this._isLoggedIn = true;
  }

  logout(): void {
    localStorage.removeItem('access_token');
    this._isLoggedIn = false;
  }

  get isLoggedInStatus(): boolean {
    return this._isLoggedIn;
  }

  get userName(): string {
    return this._userName;
  }


  public fetchUserInfo(): Observable<Userinfo> {
    return this._http.fetchUserInfo();
  }


}
