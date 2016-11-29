package services

import javax.inject._

import play.api.Logger
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.ws.{WSClient, WSResponse}

import play.api.libs.functional.syntax._


import scala.concurrent.{ExecutionContext, Future}


trait QuoteSource {
  def quotes(): Future[String]
  def getQuotes(): Future[String]
}

@Singleton
class YahooQuoteSource @Inject() (implicit context: ExecutionContext, ws: WSClient) extends QuoteSource {

  override def quotes(): Future[String] = {
    val url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22VWO,GLD,SLV%22%29"

    val futureResponse: Future[WSResponse] = ws.url(url)
        .withQueryString("format" -> "json")
        .withQueryString("env" -> "store://datatables.org/alltableswithkeys")
        .get()

    futureResponse.map(response => {
        (response.json \ "query" \ "results" \ "quote" \\ "symbol").map(_.as[String]).mkString(" ")
    })
  }

  override def getQuotes() : Future[String] = {
    val url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22VWO,GLD,SLV%22%29"

    val futureQuote: Future[WSResponse] = ws.url(url)
      .withQueryString("format" -> "json")
      .withQueryString("env" -> "store://datatables.org/alltableswithkeys")
      .get()

    futureQuote map { quotes =>
      parseYahooQuote(quotes.json)
    }
  }


  case class Quote (name: String, symbol: String, bid: Double, currency: String, yearLow: Double, yearHigh: Double, percentChange: String)
  case class Query (count: Int, quotes: Seq[Quote])


  implicit val quoteReads: Reads[Quote] = (
    (JsPath \ "Name").read[String] and
      (JsPath \ "symbol").read[String] and
      (JsPath \ "Bid").read[String].map(bid => bid.toDouble) and
      (JsPath \ "Currency").read[String] and
      (JsPath \ "YearLow").read[String].map(bid => bid.toDouble) and
      (JsPath \ "YearHigh").read[String].map(bid => bid.toDouble) and
      (JsPath \ "PercentChange").read[String]
    )(Quote.apply _)

  implicit val queryReads: Reads[Query] = (
    (JsPath \ "query" \ "count").read[Int] and
    (JsPath \ "query" \ "results" \ "quote").read[Seq[Quote]]
    )(Query.apply _)


  def parseYahooQuote(json: JsValue) : String = {
    json.validate[Query] match {
      case c: JsSuccess[Query] => {
        val query: Query = c.get
        Logger.info("Successfully parsed Quotes")
        query.quotes.mkString(" ")
      }
      case e: JsError => {
        Logger.info(s"Error parsing quote: ${e.toString}")
        "Error"
      }
    }

  }
}
