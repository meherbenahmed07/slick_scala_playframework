package controllers

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import javax.inject.Inject
import models.{Person, PersonRepository, StoreRepository, store}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.{ExecutionContext, Future}

class StoreController @Inject()(repo: StoreRepository)(implicit ec: ExecutionContext)extends Controller{
  @Inject
  var store: store = _
  protected implicit def implicitPerson = store.writes
  def addStore = Action.async{
    implicit request =>
    val form: Form[CreateStoreForm] = Form {
      mapping(
        "name" -> nonEmptyText,
        "address" -> nonEmptyText
      )(CreateStoreForm.apply)(CreateStoreForm.unapply)
    }

    form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(Json.toJson("ee"))),
      data => {
        repo.create(data.name, data.address).map {
          response => {
            Ok(Json.toJson(response))
          }
        }.recover {
          case exception: Throwable => throw exception
        }
      })
  }
}
case class CreateStoreForm(name: String, address: String)
