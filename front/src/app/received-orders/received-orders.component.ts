/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { Order } from '../shared/interfaces/Order';
import { OrdersService } from '../shared/services/orders.service';
import { Article } from '../shared/interfaces/Article';
import { ArticlesService } from '../shared/services/articles.service';
import { UpdateOrderComponent } from '../update-order/update-order.component';

@Component({
  selector: 'app-received-orders',
  templateUrl: './received-orders.component.html',
  styleUrls: ['./received-orders.component.css']
})
export class ReceivedOrdersComponent implements OnInit {

  private _orders: Order[];
  private _curArticles: Article[];

  UpdateOrderComponent = UpdateOrderComponent;

  constructor(private _ordersService: OrdersService, private _articleService: ArticlesService) {
    this._orders = [];
    this._curArticles = [];
  }

  ngOnInit(): void {
    this._ordersService.fetchAllBySeller().subscribe(
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
  }

  public get orders(): Order[] {
    return this._orders;
  }

  public fetchArticle(articleId: number): Article {
    for (let i = 0 ; i < this._curArticles.length ; i++) {
      if (articleId === Number(this._curArticles[i].id)) {
        return this._curArticles[i];
      }
    }
    return {} as Article;
  }

}
