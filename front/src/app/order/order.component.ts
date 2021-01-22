/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {OrdersService} from '../shared/services/orders.service';
import {ArticlesService} from '../shared/services/articles.service';
import {Article} from '../shared/interfaces/Article';
import {Order} from '../shared/interfaces/Order';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  private _article: Article;
  private _order: Order;

  constructor(private _ordersService: OrdersService, private _articlesService: ArticlesService, private _route: ActivatedRoute) {
    this._article = {} as Article;
    this._order = {} as Order;
  }

  ngOnInit(): void {
    this._route.paramMap.subscribe(params => {
      const id = params.get('id');
      this._ordersService.fetchOne(id).subscribe((order: Order) => {
        this._order = order;
        this._articlesService.fetchOne(String(order.articleId)).subscribe((article: Article) => this._article = article);
      });
    });
  }

  public get article(): Article {
    return this._article;
  }

  public get order(): Order {
    return this._order;
  }

}
