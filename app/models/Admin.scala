package models

import play.api.libs.json.{Json, OFormat}

case class Admin(id: String, password: String)

object Admin {
  implicit val format: OFormat[Admin] = Json.format[Admin]
}