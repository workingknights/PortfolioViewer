import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent }   from './dashboard.component';
import { HoldingsComponent }    from './holdings.component';
import { HoldingDetailComponent }    from './holding-detail.component';
import { NewHoldingComponent }    from './new-holding.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard',  component: DashboardComponent },
  { path: 'detail/:id', component: HoldingDetailComponent },
  { path: 'holdings',   component: HoldingsComponent },
  { path: 'newHolding',   component: NewHoldingComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ],
})
export class AppRoutingModule {}
