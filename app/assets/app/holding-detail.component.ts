import 'rxjs/add/operator/switchMap';
import { Component, OnInit }      from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location }               from '@angular/common';

import { Holding }        from './holding';
import { HoldingService } from './holding.service';

@Component({
//  moduleId: module.id,
  selector: 'my-holding-detail',
  templateUrl: 'assets/app/holding-detail.component.html',
  styleUrls: [ 'assets/app/holding-detail.component.css' ],
})
export class HoldingDetailComponent implements OnInit {
  public holding: Holding;

  constructor(
    private holdingService: HoldingService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  public ngOnInit(): void {
    this.route.params // tslint:disable-next-line:no-string-literal
      .switchMap((params: Params) => this.holdingService.getHolding(+params['id']))
      .subscribe(holding => this.holding = holding);
  }

  public save(): void {
    this.holdingService.update(this.holding)
      .then(() => this.goBack());
  }

  public goBack(): void {
    this.location.back();
  }
}
