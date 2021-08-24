package models

import play.api.libs.json.{JsError, JsResult, JsSuccess, JsValue, Json, OFormat, Reads}

import java.util.UUID

case class Movie(id: String, plot: String, genres: List[String], rated: String, cast: List[String], poster: String, title: String)

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
        plot <- (json \ "plot").asOpt[String]
        genres <- (json \ "genres").asOpt[List[String]]
        rated <- (json \ "rated").asOpt[String]
        cast <- (json \ "cast").asOpt[List[String]]
        poster <- (json \ "poster").asOpt[String]
        title <- (json \ "title").asOpt[String]
      } yield Movie(id, plot, genres, rated, cast, poster, title)
      result match {
        case Some(x) => JsSuccess(x)
        case _ => JsError("ERROR")
      }
    }
  }
}

