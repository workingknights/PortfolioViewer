export class Holding {
  constructor(
    public id: string,
    public symbol: string,
    public shares: number,
    public price: number
    // public tradeDate: string;
  ) { }
}
