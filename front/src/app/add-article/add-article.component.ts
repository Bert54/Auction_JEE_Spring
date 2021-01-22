/* tslint:disable:variable-name */
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Article } from '../shared/interfaces/Article';
import { ArticlesService } from '../shared/services/articles.service';
import { Router } from '@angular/router';
import { Categories } from '../shared/data/categories';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css']
})
export class AddArticleComponent implements OnInit {

  private readonly _form: FormGroup;

  private readonly _categories: string[];

  constructor(private _articlesService: ArticlesService, private _router: Router) {
    this._form = this.buildForm();
    this._categories = Categories;
  }

  ngOnInit(): void {
  }

  public get form(): FormGroup {
    return this._form;
  }

  public get categories(): string[] {
    return this._categories;
  }

  private buildForm(): FormGroup {
    return new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl(''),
      startingPrice: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]+\(\.[0-9]{1,2}\)?$'),
      ])),
      categories: new FormControl('', Validators.required),
      endingDate: new FormControl('', Validators.required)
    });
  }

  submit(article: Article): void {
    const cat: string[] = this.form.get('categories').value;
    article.categories = '';
    for (let i = 0 ; i < cat.length ; i++) {
      article.categories = article.categories.concat(cat[i]);
      if (i < cat.length - 1) {
        article.categories = article.categories.concat(',');
      }
    }
    article.endingDate =  Math.floor(this.form.get('endingDate').value.getTime() / 1000);
    article.startingPrice = Number(this.form.get('startingPrice').value);
    console.log(article);
    this._articlesService.create(article).subscribe(
      _ => this._router.navigateByUrl('articles'),
      err => console.log(err)
    );
  }

}
