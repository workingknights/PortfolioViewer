import { Component, OnInit } from '@angular/core';

import { Holding }        from './holding';
import { HoldingService } from './holding.service';

@Component({
//  moduleId: module.id,
  selector: 'my-dashboard',
  templateUrl: 'assets/app/dashboard.component.html',
  styleUrls: [ 'assets/app/dashboard.component.css' ],
})
export class DashboardComponent implements OnInit {
  public holdings: Holding[] = [];

  constructor(private holdingService: HoldingService) { }

  public ngOnInit(): void {
    this.holdingService.getHoldings()
      .then(holdings => this.holdings = holdings.slice(1, 5));
  }
}
