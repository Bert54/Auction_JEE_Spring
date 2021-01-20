/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../shared/services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private _authService: AuthenticationService) { }

  ngOnInit(): void {
    this._authService.refreshSessionStatus();
  }

  get isLoggedIn(): boolean {
    return this._authService.isLoggedInStatus;
  }

}
