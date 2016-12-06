package controllers

import javax.inject._

import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument


@Singleton
class HoldingsController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  import controllers.HoldingFields._

  def holdingRepo = new backend.PostMongoRepo(reactiveMongoApi)

  def list = Action.async {
    Logger.info("list()")
    holdingRepo.find()
      .map(holdings => Ok(toDataField(holdings)))
  }

  private def toDataField[A](value: A)(implicit writes: Writes[A]): JsValue = {
    JsObject(Seq("data" -> Json.toJson(value)))
  }

  private def RedirectAfterPost(result: WriteResult, call: Call): Result =
    if (!result.ok) InternalServerError(result.toString)
    else Redirect(call)

  def add: Action[JsValue] = Action.async(BodyParsers.parse.json) { implicit request =>
    val symbol = (request.body \ Symbol).as[String]
//    val shares = (request.body \ Shares).as[Int]
//    val price = (request.body \ Price).as[Double]
//    val tradeDate = (request.body \ TradeDate).as[String]

    holdingRepo.save(BSONDocument(
      Symbol -> symbol
//      Shares -> shares,
//      Price -> price,
//      TradeDate -> tradeDate
    )).map(le => Redirect(routes.HoldingsController.list()))
  }

  /*def saveHolding = Action(validateJson[Holding]) { request =>
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
*/
}


object HoldingFields {
  val Id = "_id"
  val Symbol = "symbol"
  val Shares = "shares"
  val Price = "price"
  val TradeDate = "tradeDate"
}
