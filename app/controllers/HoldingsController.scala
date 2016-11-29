package controllers

import javax.inject._

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.Holding

import scala.concurrent.ExecutionContext



@Singleton
class HoldingsController @Inject()(implicit context: ExecutionContext, val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def saveHolding = Action(validateJson[Holding]) { request =>
    val holding = request.body
    Holding.save(holding)
    Ok(Json.obj("status" -> "OK", "message" -> (s"Holding '${holding.symbol}' saved.")))
  }

  def listHoldings = Action {
    val json = Json.toJson(Holding.list)
    Ok(json)
  }

  def validateJson[A: Reads] = BodyParsers.parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  implicit val holdingWrites: Writes[Holding] = (
    (__ \ "symbol").write[String] and
      (__ \ "shares").write[Int] and
      (__ \ "price").write[Double] and
      (__ \ "tradeDate").write[java.util.Date]
    )(unlift(Holding.unapply))

  implicit val holdingReads: Reads[Holding] = (
    (__ \ "symbol").read[String] and
      (__ \ "shares").read[Int] and
      (__ \ "price").read[Double] and
      (__ \ "tradeDate").read[java.util.Date]
    )(Holding.apply _)

}
