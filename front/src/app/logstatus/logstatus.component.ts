/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../shared/services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logstatus',
  templateUrl: './logstatus.component.html',
  styleUrls: ['./logstatus.component.css']
})
export class LogstatusComponent implements OnInit {

  constructor(private _authService: AuthenticationService, private _router: Router) { }

  ngOnInit(): void {
    this._authService.refreshSessionStatus();
  }

  get isLoggedIn(): boolean {
    return this._authService.isLoggedInStatus;
  }

  get userName(): string {
    return this._authService.userName;
  }

  public logOut(): void {
    this._authService.logout();
    this._router.navigateByUrl('home');
  }

}
