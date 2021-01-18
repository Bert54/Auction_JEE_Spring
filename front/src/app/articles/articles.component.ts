/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../shared/services/articles.service';
import { Article } from '../shared/interfaces/Article';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  private _articles: Article[];

  constructor(private _articlesService: ArticlesService) {
    this._articles = [];
  }

  ngOnInit(): void {
    this._articlesService.fetchAll().subscribe(_ =>
      this._articles = _
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
