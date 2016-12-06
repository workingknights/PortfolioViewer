import { Component }          from '@angular/core';

@Component({
//  moduleId: module.id,
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <nav>
      <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
      <a routerLink="/holdings" routerLinkActive="active">Holdings</a>
    </nav>
    <router-outlet></router-outlet>
  `,
  styleUrls: ['assets/app/app.component.css'],
})
export class AppComponent {
  public title = 'Portfolio Viewer';
}
