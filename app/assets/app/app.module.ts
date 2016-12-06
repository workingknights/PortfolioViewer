import './rxjs-extensions';

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent }           from './app.component';
import { DashboardComponent }     from './dashboard.component';
import { HoldingsComponent }      from './holdings.component';
import { HoldingDetailComponent } from './holding-detail.component';
import { HoldingService }         from './holding.service';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    HoldingsComponent,
    HoldingDetailComponent,
  ],
  providers: [ HoldingService ],
  bootstrap: [ AppComponent ],
})
export class AppModule { }
