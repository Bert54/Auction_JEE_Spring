/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {ArticlesService} from '../shared/services/articles.service';
import {Article} from '../shared/interfaces/Article';

@Component({
  selector: 'app-user-bids',
  templateUrl: './user-bids.component.html',
  styleUrls: ['./user-bids.component.css']
})
export class UserBidsComponent implements OnInit {

  private _articles: Article[];

  constructor(private _articlesService: ArticlesService) {
    this._articles = [];
  }

  ngOnInit(): void {
    this._articlesService.fetchByUserBids().subscribe(
      _ => this._articles = _,
      _ => console.log(_)
    );
  }

  public get articles(): Article[] {
    return this._articles;
  }

  public isActive(articleTimestamp: number): boolean {
    const currentTimeInSeconds = Math.floor(Date.now() / 1000);
    return articleTimestamp >= currentTimeInSeconds;
  }

}
