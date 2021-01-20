import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ArticlesComponent } from './articles/articles.component';
import { LoggedInGuard } from './shared/guards/logged-in.guard';
import { ArticleComponent } from './article/article.component';
import { LoggedInArticleOwnershipGuard } from './shared/guards/logged-in-article-ownership.guard';
import { AddArticleComponent } from './add-article/add-article.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'articles', component: ArticlesComponent, canActivate: [LoggedInGuard] },
  { path: 'articles/details/:id', component: ArticleComponent, canActivate: [LoggedInArticleOwnershipGuard] },
  { path: 'articles/new', component: AddArticleComponent, canActivate: [LoggedInGuard] },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, { useHash: false }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
