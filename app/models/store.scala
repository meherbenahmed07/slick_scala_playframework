package models

import play.api.libs.json._

case class store1(id: String, name: String, address: String)

class store {
  val writes: Writes[store1] = Writes {
    (color: store1) => {
      var response = Json.obj(
        "id" -> color.id,
        "name" -> color.name,
        "age" -> color.address
      )
      response
    }
  }}

