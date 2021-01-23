/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {HttpRequestsService} from './http-requests.service';
import {Observable} from 'rxjs';
import {Order} from '../interfaces/Order';
import {NewOrder} from '../interfaces/NewOrder';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private _http: HttpRequestsService) {}

  public fetchAll(): Observable<Order[]> {
    return this._http.fetchAllOrders();
  }

  public fetchOne(id: string): Observable<Order> {
    return this._http.fetchOneOrder(id);
  }

  public create(order: NewOrder): Observable<any> {
    return this._http.createOrder(order);
  }

  public fetchAllBySeller(): Observable<Order[]> {
    return this._http.fetchAllOrdersBySeller();
  }

  public updateStatus(id: string): Observable<any> {
    return this._http.updateOrderStatus(id);
  }

}
