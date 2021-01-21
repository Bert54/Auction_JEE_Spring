/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../shared/services/articles.service';
import { Article } from '../shared/interfaces/Article';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Categories } from '../shared/data/categories';
import {AuthenticationService} from '../shared/services/authentication.service';

@Component({
  selector: 'app-search-article',
  templateUrl: './search-article.component.html',
  styleUrls: ['./search-article.component.css']
})
export class SearchArticleComponent implements OnInit {

  private _articles: Article[];

  private readonly _form: FormGroup;
  private readonly _categories: string[];

  constructor(private _articleService: ArticlesService, private _authService: AuthenticationService) {
    this._form = this.buildForm();
    this._categories = Categories;
  }

  ngOnInit(): void {
    this._articles = [];
  }

  public get articles(): Article[] {
    return this._articles;
  }

  public get categories(): string[] {
    return this._categories;
  }

  public get form(): FormGroup {
    return this._form;
  }

  private buildForm(): FormGroup {
    return new FormGroup({
      name: new FormControl(''),
      categories: new FormControl(''),
    });
  }

  public submit(name: string, categories: string[]): void {
    const cat: string[] = categories;
    let categoriesString = '';
    for (let i = 0 ; i < cat.length ; i++) {
      categoriesString = categoriesString.concat(cat[i]);
      if (i < cat.length - 1) {
        categoriesString = categoriesString.concat(',');
      }
    }
    this._articleService.fetchByFilter(name, categoriesString).subscribe(
      _ => this._articles = _,
      _ => console.log(_)
    );
  }

  public isLoggedIn(): boolean {
    return this._authService.isLoggedInStatus;
  }

  public isNotOwner(articleSeller: string): boolean {
    return articleSeller !== this._authService.userName;
  }

}
