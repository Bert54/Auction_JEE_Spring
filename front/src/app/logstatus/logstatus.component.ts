/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../shared/services/authentication.service';
import {Router} from '@angular/router';
import {MiscellaneousService} from '../shared/services/miscellaneous.service';
import {Offer} from '../shared/interfaces/Offer';

@Component({
  selector: 'app-logstatus',
  templateUrl: './logstatus.component.html',
  styleUrls: ['./logstatus.component.css']
})
export class LogstatusComponent implements OnInit {

  private _currentOffer: Offer;

  constructor(private _authService: AuthenticationService, private _router: Router, private _miscellaneousService: MiscellaneousService) {
    this._currentOffer = {category: '', id: '', rebate: 0};
  }

  ngOnInit(): void {
    this._authService.refreshSessionStatus();
    this._getCurrentOffer();
  }

  get isLoggedIn(): boolean {
    return this._authService.isLoggedInStatus;
  }

  get userName(): string {
    return this._authService.userName;
  }

  get offerCategory(): string {
    return this._currentOffer.category;
  }

  get offerRebate(): number {
    return this._currentOffer.rebate;
  }

  public logOut(): void {
    this._authService.logout();
    this._router.navigateByUrl('home');
  }

  private _getCurrentOffer(): void {
    this._miscellaneousService.getCurrentOffer().subscribe(
      offer => this._currentOffer = offer,
      err => console.log(err)
    );
  }

}
