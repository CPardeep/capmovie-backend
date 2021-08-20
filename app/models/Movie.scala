package models

import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Json, OFormat, Reads}

import java.util.UUID

case class Movie(id: String, name: String, year: Int, genre: String, ageRating: String, img: String, description: String)

object Movie {

  implicit val format: OFormat[Movie] = Json.format[Movie]

  def createMovieID(): String = {
    val id: String = UUID.randomUUID().toString.replace("-", "")
    "MOV" + id.substring(id.length() - 4).toUpperCase()
  }

  val newMovieReads: Reads[Movie] = new Reads[Movie] {
    def reads(json: JsValue): JsResult[Movie] = {
      val result = for {
        id <- Some(createMovieID())
        name <- (json \ "name").asOpt[String]
        year <- (json \ "year").asOpt[Int]
        genre <- (json \ "genre").asOpt[String]
        ageRating <- (json \ "ageRating").asOpt[String]
        img <- (json \ "img").asOpt[String]
        description <- (json \ "description").asOpt[String]
      } yield (Movie(id, name, year, genre, ageRating, img, description))
      result match {
        case Some(x) => JsSuccess(x)
        case _ => JsError("ERROR")
      }
    }
  }
}

