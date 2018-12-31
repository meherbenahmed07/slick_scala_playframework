package models
import java.sql.Timestamp
import java.time.ZoneOffset

import javax.inject.Inject
import play.api.libs.json._
import slick.driver.MySQLDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import org.joda.time.{DateTime, DateTimeZone}
import slick.jdbc.{GetResult, PositionedParameters, PositionedResult}
case class Person1(id: String, name: String, age: Int,date:DateTime,store:String)
import scala.util.{Success, Failure}
class Person {
  @Inject
  var store: store = _
  protected implicit def implicitPerson = store.writes
  val writes: Writes[Person1] = Writes {
    (color: Person1) => {
      implicit val getSupplierResult = GetResult(r => List[store1](store1(r.nextString, r.nextString, r.nextString)))
      implicit object JodaDateTimeWrites extends Writes[DateTime] {
        def writes(d: DateTime): JsValue = JsString(d.toString)
      }
      val s=sql"select s.id,s.name,s.age FROM store s join UUIDTest j on s.id=j.fk_store where s.id=${color.store}".as[(String,String,String)]
      var e =connection.db.run(s)
      var response = Json.obj(
        "id" -> color.id,
        "name" -> color.name,
        "age" -> color.age,
        "date"->Json.toJson(color.date)
      )
      e.map {
        response1 => {
          response+=("p"->Json.arr(Json.toJson(response1)))
          print("ee")
        }
      }.recover {
        case exception: Throwable => throw exception
      }
      response
    }
  }}
