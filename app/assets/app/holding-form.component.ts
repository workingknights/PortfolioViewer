import { Component } from '@angular/core';
import { Router }            from '@angular/router';

import { Holding }                from './holding';
import { HoldingService }         from './holding.service';

@Component({
  // moduleId: module.id,
  selector: 'holding-form',
  templateUrl: 'assets/app/holding-form.component.html',
})
export class HoldingFormComponent {

  protected model = new Holding('1', 'AAPL', 1, 1.0);
  private submitted = false;

  constructor(
    private holdingService: HoldingService,
    private router: Router) {
  }

  protected onSubmit() {
    this.submitted = true;

    let symbol =  this.model.symbol.trim();
    if (!symbol) { return; }

    this.holdingService.create(this.model);
  }

  protected newHolding() {
    this.model = new Holding('1', '', 0, 0.0);
  }
}
