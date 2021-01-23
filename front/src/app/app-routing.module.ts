import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ArticlesComponent } from './articles/articles.component';
import { LoggedInGuard } from './shared/guards/logged-in.guard';
import { ArticleComponent } from './article/article.component';
import { AddArticleComponent } from './add-article/add-article.component';
import { SearchArticleComponent } from './search-article/search-article.component';
import { UserBidsComponent } from './user-bids/user-bids.component';
import { BidArticleComponent } from './bid-article/bid-article.component';
import {ArticleActiveGuard} from './shared/guards/article-active.guard';
import {OrdersComponent} from './orders/orders.component';
import {OrderComponent} from './order/order.component';
import {CreateOrderComponent} from './create-order/create-order.component';
import {OrderViewableGuard} from './shared/guards/order-viewable.guard';
import {ArticleNotOrderedGuard} from './shared/guards/article-not-ordered.guard';
import {ReceivedOrdersComponent} from './received-orders/received-orders.component';
import {UpdateOrderComponent} from './update-order/update-order.component';
import {CanUpdateOrderGuard} from './shared/guards/can-update-order.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'articles', component: ArticlesComponent, canActivate: [LoggedInGuard] },
  { path: 'articles/details/:id', component: ArticleComponent },
  { path: 'articles/new', component: AddArticleComponent, canActivate: [LoggedInGuard] },
  { path: 'articles/search', component: SearchArticleComponent },
  { path: 'articles/mybids', component: UserBidsComponent, canActivate: [LoggedInGuard] },
  { path: 'articles/bid/:id', component: BidArticleComponent, canActivate: [LoggedInGuard, ArticleActiveGuard] },
  { path: 'orders', component: OrdersComponent, canActivate: [LoggedInGuard] },
  { path: 'orders/:id', component: OrderComponent, canActivate: [LoggedInGuard, OrderViewableGuard] },
  { path: 'orders/new/:id', component: CreateOrderComponent, canActivate: [LoggedInGuard, ArticleNotOrderedGuard] },
  { path: 'orders/received/all', component: ReceivedOrdersComponent, canActivate: [LoggedInGuard] },
  { path: 'orders/received/update/:id', component: UpdateOrderComponent, canActivate: [LoggedInGuard, CanUpdateOrderGuard] },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, { useHash: false }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
