/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {ArticlesService} from '../services/articles.service';
import {AuthenticationService} from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleActiveGuard implements CanActivate {

  constructor(private _router: Router, private _articlesService: ArticlesService, private _authService: AuthenticationService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const id = route.params.id;
    const subject = new Subject<boolean>();
    this._articlesService.fetchOne(id).
    subscribe(
      _ => {
        const currentTimeInSeconds = Math.floor(Date.now() / 1000);
        subject.next(_.seller !== this._authService.userName && _.endingDate >= currentTimeInSeconds);
      },
      _ => {
        this._router.navigateByUrl('/home');
        subject.next(false);
      }
    );
    return subject.asObservable();
  }
}
