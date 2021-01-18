/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../interfaces/User';
import {Username} from '../interfaces/Username';
import {JWT} from '../interfaces/JWT';
import {Article} from '../interfaces/Article';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {

  private readonly _backendURL: any;

  constructor(private _http: HttpClient) {

    this._backendURL = {};


    let baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
    if (environment.backend.port) {
      baseUrl += `:${environment.backend.port}`;
    }
    Object.keys(environment.backend.endpoints).forEach(k => this._backendURL[ k ] = `${baseUrl}${environment.backend.endpoints[ k ]}`);
  }

  public login(user: User): Observable<any> {
    return this._http.post<JWT>(this._backendURL.login, user, this._options());
  }

  public fetchLoginUserName(): Observable<Username> {
    return this._http.get<Username>(this._backendURL.users);
  }

  public createUser(user: User): Observable<any> {
    return this._http.post<User>(this._backendURL.signup, user, this._options());
  }

  public fetchAllArticles(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.allArticles);
  }

  public fetchOneArticle(id: string): Observable<Article> {
    return this._http.get<Article>(this._backendURL.oneArticle.replace(':id', id));
  }

  public createArticle(article: Article): Observable<any> {
    return this._http.post<Article>(this._backendURL.addOneArticle, article, this._options());
  }

  private _options(headerList: object = {}): any {
    return { headers: new HttpHeaders(Object.assign({ 'Content-Type': 'application/json' }, headerList)) };
  }

}
