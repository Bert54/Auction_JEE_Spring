/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {ArticlesService} from '../services/articles.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleNotOrderedGuard implements CanActivate {

  constructor(private _router: Router, private _articlesService: ArticlesService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const id = route.params.id;
    const subject = new Subject<boolean>();
    this._articlesService.fetchBuyable().
    subscribe(
      articles => {
        let isBuyable = false;
        for (let i = 0 ; i < articles.length ; i++) {
          if (String(articles[i].id) === String(id)) {
            isBuyable = true;
          }
        }
        if (isBuyable) {
          subject.next(true);
        }
        else {
          this._router.navigateByUrl('/home');
          subject.next(false);
        }
      },
      _ => {
        this._router.navigateByUrl('/home');
        subject.next(false);
      }
    );
    return subject.asObservable();

  }

}
