import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { ArticlesComponent } from './articles/articles.component';
import { LogstatusComponent } from './logstatus/logstatus.component';
import { MatListModule} from '@angular/material/list';
import { ArticleComponent } from './article/article.component';
import { AddArticleComponent } from './add-article/add-article.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {
  NgxMatDatetimePickerModule,
  NgxMatNativeDateModule,
  NgxMatTimepickerModule
} from '@angular-material-components/datetime-picker';
import { MatSelectModule } from '@angular/material/select';
import { SearchArticleComponent } from './search-article/search-article.component';
import { UserBidsComponent } from './user-bids/user-bids.component';
import { BidArticleComponent } from './bid-article/bid-article.component';
import { OrdersComponent } from './orders/orders.component';
import { OrderComponent } from './order/order.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { UpdateOrderComponent } from './update-order/update-order.component';
import { ReceivedOrdersComponent } from './received-orders/received-orders.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    ArticlesComponent,
    LogstatusComponent,
    ArticleComponent,
    AddArticleComponent,
    SearchArticleComponent,
    UserBidsComponent,
    BidArticleComponent,
    OrdersComponent,
    OrderComponent,
    CreateOrderComponent,
    UpdateOrderComponent,
    ReceivedOrdersComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatIconModule,
    MatInputModule,
    MatDividerModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxMatDatetimePickerModule,
    NgxMatNativeDateModule,
    NgxMatTimepickerModule,
    MatOptionModule,
    MatSelectModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
