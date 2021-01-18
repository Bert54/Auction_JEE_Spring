/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {HttpRequestsService} from './http-requests.service';
import {Observable} from 'rxjs';
import {Article} from '../interfaces/Article';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  constructor(private _http: HttpRequestsService) {}

  public fetchAll(): Observable<Article[]> {
    return this._http.fetchAllArticles();
  }

  public fetchOne(id: string): Observable<Article> {
    return this._http.fetchOneArticle(id);
  }

  public create(article: Article): Observable<any> {
    return this._http.createArticle(article);
  }

  public delete(id: string): Observable<Article> {
    return this._http.deleteArticle(id);
  }



}
