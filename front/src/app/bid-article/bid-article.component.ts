/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {Article} from '../shared/interfaces/Article';
import {ArticlesService} from '../shared/services/articles.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-bid-article',
  templateUrl: './bid-article.component.html',
  styleUrls: ['./bid-article.component.css']
})
export class BidArticleComponent implements OnInit {

  private _article: Article;

  private readonly _form: FormGroup;

  private _amountInvalid: boolean;

  constructor(private _articlesService: ArticlesService, private _route: ActivatedRoute, private _router: Router) {
    this._article = {} as Article;
    this._form = this.buildForm();
  }

  ngOnInit(): void {
    this._amountInvalid = false;
    this._route.paramMap.subscribe(params => {
      const id = params.get('id');
      this._articlesService.fetchOne(id).subscribe((article: Article) => this._article = article);
    });
  }

  public get article(): Article {
    return this._article;
  }

  public get form(): FormGroup {
    return this._form;
  }

  public get amountInvalid(): boolean {
    return this._amountInvalid;
  }

  public submit(amount: number): void {
    if (amount <= this._article.currentPrice) {
      this._amountInvalid = true;
      return;
    }
    this._route.paramMap.subscribe(params => {
      this._articlesService.sendNewBid({
        id: Number(params.get('id')),
        amount
      }).subscribe(
        _ => this._router.navigateByUrl('/articles/mybids'),
        _ => console.log(_)
      );
    });
  }

  private buildForm(): FormGroup {
    return new FormGroup({
      amount: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]+\(\.[0-9]{1,2}\)?$'),
      ])),
    });
  }

}
