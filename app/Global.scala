import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.{Application, GlobalSettings, Logger}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection

class Global @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends GlobalSettings {

  def collection = reactiveMongoApi.db.collection[JSONCollection]("holdings")

  val holdings = List(
    Json.obj(
      "symbol" -> "AAPL",
      "shares" -> 10,
      "price" -> 66.7,
      "tradeDate" -> "2016-12-05"
    )
  )

  override def onStart(app: Application) {
    Logger.info("Application has started")

    collection.bulkInsert(holdings.toStream, ordered = true).
      foreach(i => Logger.info("Database was initialized"))
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")

    collection.drop().onComplete {
      case _ => Logger.info("Database collection dropped")
    }
  }
}