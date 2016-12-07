import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import { Holding }                from './holding';
import { HoldingService }         from './holding.service';

@Component({
//  moduleId: module.id,
  selector: 'my-holdings',
  templateUrl: 'assets/app/holdings.component.html',
  styleUrls: [ 'assets/app/holdings.component.css' ],
})
export class HoldingsComponent implements OnInit {
  public holdings: Holding[];
  public selectedHolding: Holding;

  constructor(
    private holdingService: HoldingService,
    private router: Router) { }

  public delete(holding: Holding): void {
    this.holdingService
        .delete(holding.id)
        .then(() => {
          this.holdings = this.holdings.filter(h => h !== holding);
          if (this.selectedHolding === holding) { this.selectedHolding = null; }
        });
  }

  public ngOnInit(): void {
    this.getHoldings();
  }

  public onSelect(holding: Holding): void {
    this.selectedHolding = holding;
  }

  public gotoDetail(): void {
    this.router.navigate(['/detail', this.selectedHolding.id]);
  }

  private getHoldings(): void {
    this.holdingService
      .getHoldings()
      .then(holdings => this.holdings = holdings);
  }
}
