/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import {ArticlesService} from '../shared/services/articles.service';
import {Article} from '../shared/interfaces/Article';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  private _article: Article;

  constructor(private _articlesService: ArticlesService, private _route: ActivatedRoute) {
    this._article = {} as Article;
  }

  ngOnInit(): void {
    this._route.paramMap.subscribe(params => {
      const id = params.get('id');
      this._articlesService.fetchOne(id).subscribe((article: Article) => this._article = article);
    });
  }

  public get article(): Article {
    return this._article;
  }

}
