package controllers

import javax.inject.Inject
import models.{Person, PersonRepository}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
class PersonController @Inject()(repo: PersonRepository)(implicit ec: ExecutionContext)extends Controller
{
  @Inject
  var person: Person = _
  protected implicit def implicitPerson = person.writes
  def addPerson = Action.async (parse.urlFormEncoded){ implicit request =>
    val form: Form[CreatePersonForm] = Form {
      mapping(
        "name" -> nonEmptyText,
        "age" -> number,
        "createddate"->longNumber,
        "store" -> nonEmptyText
      )(CreatePersonForm.apply)(CreatePersonForm.unapply)
    }

    form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(Json.toJson("ee"))),
      data => {
        repo.create(data.name, data.age,data.date,data.store).map {
          response => {
            Ok(Json.toJson(response))
          }
        }.recover {
          case exception: Throwable => throw exception
        }
      })
  }
}

case class CreatePersonForm(name: String, age: Int,date:Long,store:String)


