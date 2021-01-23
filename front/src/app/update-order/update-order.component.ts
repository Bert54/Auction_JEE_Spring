/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { OrdersService } from '../shared/services/orders.service';
import { Order } from '../shared/interfaces/Order';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-order',
  templateUrl: './update-order.component.html',
  styleUrls: ['./update-order.component.css']
})
export class UpdateOrderComponent implements OnInit {

  public static readonly ORDERSTEPONE = 'Order sent';
  public static readonly ORDERSTEPTWO = 'Order confirmed';

  private _order: Order;

  UpdateOrderComponent = UpdateOrderComponent;

  constructor(private _ordersService: OrdersService, private _route: ActivatedRoute, private _router: Router) {
    this._order = {} as Order;
  }

  public get order(): Order {
    return this._order;
  }

  ngOnInit(): void {
    this._route.paramMap.subscribe(params => {
      const id = params.get('id');
      this._ordersService.fetchOne(id).subscribe(
        (order: Order) => this._order = order,
        err => console.log(err)
        );
    });
  }

  public updateOrder(): void {
    this._ordersService.updateStatus(this._order.id).subscribe(
      _ => this._router.navigateByUrl('orders/received/all'),
      err => console.log(err)
    );
  }

}
