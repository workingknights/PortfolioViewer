package services

import javax.inject._

import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}

trait QuoteSource {
  def quotes(): Future[String]
}

@Singleton
class YahooQuoteSource  @Inject() (implicit context: ExecutionContext, ws: WSClient) extends QuoteSource {

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

}