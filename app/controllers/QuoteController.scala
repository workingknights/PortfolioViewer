package controllers

import javax.inject._

import play.api.mvc._
import services.QuoteSource

import scala.concurrent.ExecutionContext

@Singleton
class QuoteController @Inject()(implicit context: ExecutionContext, quoteSource: QuoteSource) extends Controller {

  def quotes = Action.async{ request =>
    quoteSource.quotes().map(response => {
      Ok(response)
    })
  }
}
