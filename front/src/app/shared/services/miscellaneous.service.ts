/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {HttpRequestsService} from './http-requests.service';
import {Observable} from 'rxjs';
import {Offer} from '../interfaces/Offer';

@Injectable({
  providedIn: 'root'
})
export class MiscellaneousService {

  constructor(private _http: HttpRequestsService) {}

  public getCurrentOffer(): Observable<Offer> {
    return this._http.getCurrentOffer();
  }

}
