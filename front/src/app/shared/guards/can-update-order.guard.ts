/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {ArticlesService} from '../services/articles.service';
import {AuthenticationService} from '../services/authentication.service';
import {OrdersService} from '../services/orders.service';

@Injectable({
  providedIn: 'root'
})
export class CanUpdateOrderGuard implements CanActivate {

  constructor(private _router: Router, private _articlesService: ArticlesService, private _authService: AuthenticationService,
              private _ordersService: OrdersService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const id = route.params.id;
    const subject = new Subject<boolean>();
    this._ordersService.fetchOne(id).
    subscribe(
      order => {
        this._articlesService.fetchOne(String(order.articleId)).subscribe(
          article => subject.next(article.seller === this._authService.userName),
          __ => {
            this._router.navigateByUrl('/home');
            subject.next(false);
          }
        );
      },
      _ => {
        this._router.navigateByUrl('/home');
        subject.next(false);
      }
    );
    return subject.asObservable();

  }

}
