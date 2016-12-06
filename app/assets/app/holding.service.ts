import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Holding } from './holding';

@Injectable()
export class HoldingService {

  private headers = new Headers({'Content-Type': 'application/json'});
  private holdingsUrl = 'api/holdings';

  constructor(private http: Http) { }

  public getHoldings(): Promise<Holding[]> {
    return this.http.get(this.holdingsUrl)
               .toPromise()
               .then(response => response.json().data as Holding[])
               .catch(this.handleError);
  }

  public getHolding(id: string): Promise<Holding> {
    return this.getHoldings()
               .then(holdings => holdings.find(holding => holding.id === id));
  }

  public delete(id: string): Promise<void> {
    const url = `${this.holdingsUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  public create(symbol: string): Promise<Holding> {
    return this.http
      .post(this.holdingsUrl, JSON.stringify({symbol: symbol}), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  public update(holding: Holding): Promise<Holding> {
    const url = `${this.holdingsUrl}/${holding.id}`;
    return this.http
      .put(url, JSON.stringify(holding), {headers: this.headers})
      .toPromise()
      .then(() => holding)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
