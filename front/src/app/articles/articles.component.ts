/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../shared/services/articles.service';
import { Article } from '../shared/interfaces/Article';
import {Router} from '@angular/router';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  private _articles: Article[];

  constructor(private _articlesService: ArticlesService, private _router: Router) {
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

  public deleteArticle(id: string): void {
    this._articlesService.delete(id).subscribe(
      _ => this._articlesService.fetchAll().subscribe(
        __ => this._articles = __,
        err2 => console.log(err2)
      ),
    err1 => console.log(err1)
    );
  }
}
