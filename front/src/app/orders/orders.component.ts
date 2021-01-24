/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { OrdersService } from '../shared/services/orders.service';
import { Order } from '../shared/interfaces/Order';
import { ArticlesService } from '../shared/services/articles.service';
import { Article } from '../shared/interfaces/Article';
import { Offer } from '../shared/interfaces/Offer';
import { MiscellaneousService } from '../shared/services/miscellaneous.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  private _orders: Order[];
  private _curArticles: Article[];

  private _articlesForSale: Article[];

  private _currentOffer: Offer;

  constructor(private _ordersService: OrdersService, private _articleService: ArticlesService,
              private _miscellaneousService: MiscellaneousService) {
    this._currentOffer = {category: '', id: '', rebate: 0};
    this._orders = [];
    this._curArticles = [];
    this._articlesForSale = [];
  }

  ngOnInit(): void {
    this._ordersService.fetchAll().subscribe(
      orders => {
        this._orders = orders;
        for (let i = 0 ; i < orders.length ; i++) {
          this._articleService.fetchOne(String(orders[i].articleId)).subscribe(
            article => this._curArticles.push(article),
            err2 => console.log(err2)
          );
        }
      },
      err => console.log(err)
    );
    this._articleService.fetchBuyable().subscribe(
      articles => this._articlesForSale = articles,
      err => console.log (err),
    );
    this._miscellaneousService.getCurrentOffer().subscribe(
      offer => this._currentOffer = offer,
      err => console.log(err)
    );
  }

  public get orders(): Order[] {
    return this._orders;
  }

  public get articlesForSale(): Article[] {
    return this._articlesForSale;
  }

  public fetchArticle(articleId: number): Article {
    for (let i = 0 ; i < this._curArticles.length ; i++) {
      if (articleId === Number(this._curArticles[i].id)) {
        return this._curArticles[i];
      }
    }
    return {} as Article;
  }

  public isRebateApplicable(article: Article): boolean {
    const articleCategories = article.categories.split(',');
    if (article.endingDate > Math.floor(Date.now() / 1000) + 259200) {
      return false;
    }
    for (let i = 0 ; i < articleCategories.length ; i++) {
      if (this._currentOffer.category === articleCategories[i]) {
        return true;
      }
    }
    return false;
  }

  public calculateRebate(articlePrice: number): number {
    const percentage = (100 - this._currentOffer.rebate) / 100;
    return articlePrice * percentage;
  }

}
