package utils

import models.Movie
import play.api.libs.json.{JsValue, Json}

object TestObjects {

  val movie: Movie = Movie(
    id = "MOV234",
    name = "testMov",
    year = 1111,
    genre = "testGenre",
    ageRating = "testAgeRating",
    img = "testImg",
    description = "testDesc")

  val movieJson: JsValue = Json.parse(
    s"""{
      |    "${MovieFields.id}" : "${movie.id}",
      |    "${MovieFields.name}" : "${movie.name}",
      |    "${MovieFields.year}" : ${movie.year},
      |    "${MovieFields.genre}" : "${movie.genre}",
      |    "${MovieFields.ageRating}" : "${movie.ageRating}",
      |    "${MovieFields.img}" : "${movie.img}",
      |    "${MovieFields.description}" : "${movie.description}"
      |}
      |""".stripMargin)

}
