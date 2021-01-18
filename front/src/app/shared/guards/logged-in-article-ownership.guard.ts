/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {ArticlesService} from '../services/articles.service';

@Injectable({
  providedIn: 'root'
})
export class LoggedInArticleOwnershipGuard implements CanActivate {

  constructor(private _router: Router, private _articleService: ArticlesService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const idToken = localStorage.getItem('access_token');
    if (idToken) {
      const id = route.params.id;
      const subject = new Subject<boolean>();
      this._articleService.fetchOne(id).
      subscribe(
        _ => {
          subject.next(true);
        },
        _ => {
          this._router.navigateByUrl('articles');
          subject.next(false);
        }
      );
      return subject.asObservable();
    }
    this._router.navigateByUrl('articles');
    return false;
  }

}
