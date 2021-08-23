package models

import play.api.libs.json.{JsSuccess, JsValue, Json}
import utils.MovieFields

class MovieSpec extends AbstractModelsTest {
  val movie: Movie = Movie(
    id = "TESTMOV",
    plot = "Test plot",
    genres = List(
      "testGenre1",
      "testGenre2"),
    rated = "testRating",
    cast = List(
      "testPerson",
      "TestPerson"),
    poster = "testURL",
    title = "testTitle")
  val movieJson: JsValue = Json.parse(
    s"""{
       |    "${MovieFields.id}" : "${movie.id}",
       |    "${MovieFields.plot}" : "${movie.plot}",
       |    "${MovieFields.genres}" : [
       |       "${movie.genres.head}",
       |       "${movie.genres(1)}"
       |    ],
       |    "${MovieFields.rated}" : "${movie.rated}",
       |    "${MovieFields.cast}" : [
       |       "${movie.cast.head}",
       |       "${movie.cast(1)}"
       |    ],
       |    "${MovieFields.poster}" : "${movie.poster}",
       |    "${MovieFields.title}" : "${movie.title}"
       |}
       |""".stripMargin)

  "Movie" can {
    "OFormat" should {
      "convert object to json" in {
        Json.toJson(movie).toString shouldBe movieJson.toString
      }
      "convert json to object" in {
        Json.fromJson[Movie](movieJson).get.toString shouldBe JsSuccess(movie).get.toString
      }
    }
  }
}
