/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {ArticlesService} from '../shared/services/articles.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatchValidators} from '../shared/validators/MatchValidators';
import {Article} from '../shared/interfaces/Article';
import {ActivatedRoute, Router} from '@angular/router';
import {OrdersService} from '../shared/services/orders.service';
import {NewOrder} from '../shared/interfaces/NewOrder';
import {AuthenticationService} from '../shared/services/authentication.service';
import {Offer} from '../shared/interfaces/Offer';
import {MiscellaneousService} from '../shared/services/miscellaneous.service';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {

  private readonly _form: FormGroup;

  private _article: Article;
  private _currentOffer: Offer;

  constructor(private _articlesService: ArticlesService, private _ordersService: OrdersService, private _route: ActivatedRoute,
              private _router: Router, private _authService: AuthenticationService, private _miscellaneousService: MiscellaneousService) {
    this._article = {} as Article;
    this._form = this.buildForm();
    this._currentOffer = {} as Offer;
  }

  ngOnInit(): void {
    this._route.paramMap.subscribe(params => {
      const id = params.get('id');
      this._articlesService.fetchOne(id).subscribe((article: Article) => this._article = article);
    });
    this._authService.fetchUserInfo().subscribe(
      userinfo => this._form.patchValue(userinfo),
      err => console.log(err)
    );
    this._miscellaneousService.getCurrentOffer().subscribe(
      offer => this._currentOffer = offer,
      err => console.log(err)
    );
  }

  public get form(): FormGroup {
    return this._form;
  }

  public get article(): Article {
    return this._article;
  }

  public submit(): void {
    const order: NewOrder = {
      articleId: Number(this._article.id),
      city: this.form.get('city').value,
      firstname: this.form.get('firstName').value,
      lastname: this.form.get('lastName').value,
      streetName: this.form.get('street').value,
      streetNumber: this.form.get('houseNumber').value,
      zipcode: this.form.get('postcode').value
    };
    this._ordersService.create(order).subscribe(
      _ => this._router.navigateByUrl('orders'),
      err => console.log(err)
    );
  }

  public isRebateApplicable(): boolean {
    if (this._article.categories === undefined) {
      return false;
    }
    const articleCategories = this._article.categories.split(',');
    if (this._article.endingDate > Math.floor(Date.now() / 1000) + 259200) {
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

  private buildForm(): FormGroup {
    return new FormGroup({
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        street: new FormControl('', Validators.required),
        city: new FormControl('', Validators.required),
        postcode: new FormControl('', Validators.pattern('(^[0-9]{5}|$)')),
        houseNumber: new FormControl('', Validators.pattern('(^[0-9]+|$)')),
      });
  }

}
