package services

case class Holding (symbol: String, shares: Int, price: Double, tradeDate: java.util.Date)

object Holding {

  var list: List[Holding] = List[Holding]()

  def save(holding: Holding) = {
    list = list ::: List(holding)
  }
}
