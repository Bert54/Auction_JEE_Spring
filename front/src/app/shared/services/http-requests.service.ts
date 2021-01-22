/* tslint:disable:variable-name */
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {User} from '../interfaces/User';
import {Username} from '../interfaces/Username';
import {JWT} from '../interfaces/JWT';
import {Article} from '../interfaces/Article';
import {Bid} from '../interfaces/Bid';
import {Offer} from '../interfaces/Offer';
import {Order} from '../interfaces/Order';
import {NewOrder} from '../interfaces/NewOrder';
import {Userinfo} from '../interfaces/Userinfo';

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

  public deleteArticle(id: string): Observable<Article> {
    return this._http.delete<Article>(this._backendURL.deleteOneArticle.replace(':id', id));
  }

  public fetchArticlesFilter(name: string, categories: string): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.searchArticles, {
        params: {
          name,
          categories
        }
      }
    );
  }

  public fetchArticlesUserBids(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.userBids);
  }

  public sendNewBidOnArticle(newBid: Bid): Observable<any> {
    return this._http.post<Article>(this._backendURL.newBid, newBid, this._options());
  }

  public fetchCurrentOffer(): Observable<Offer> {
    return this._http.get<Offer>(this._backendURL.offers);
  }

  public fetchAllOrders(): Observable<Order[]> {
    return this._http.get<Order[]>(this._backendURL.allOrders);
  }

  public fetchBuyableArticles(): Observable<Article[]> {
    return this._http.get<Article[]>(this._backendURL.buyableArticles);
  }

  public fetchOneOrder(id: string): Observable<Order> {
    return this._http.get<Order>(this._backendURL.oneOrder.replace(':id', id));
  }

  public createOrder(order: NewOrder): Observable<any> {
    return this._http.post<any>(this._backendURL.allOrders, order, this._options());
  }

  public fetchUserInfo(): Observable<Userinfo> {
    return this._http.get<Userinfo>(this._backendURL.userinfo);
  }


  private _options(headerList: object = {}): any {
    return { headers: new HttpHeaders(Object.assign({ 'Content-Type': 'application/json' }, headerList)) };
  }

}
