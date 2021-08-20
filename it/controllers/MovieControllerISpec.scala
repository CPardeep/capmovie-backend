package controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status.{BAD_REQUEST, CREATED}
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo
import utils.MovieFields
import utils.TestObjects.movie

class MovieControllerISpec extends AnyWordSpec with Matchers with GuiceOneServerPerSuite {
  val ws: WSClient = app.injector.instanceOf[WSClient]
  val controller: MovieController = app.injector.instanceOf[MovieController]
  val db: MovieRepo = app.injector.instanceOf[MovieRepo]
  val json: JsValue = Json.parse(
    s"""{
       |    "${MovieFields.name}" : "${movie.name}",
       |    "${MovieFields.year}" : ${movie.year},
       |    "${MovieFields.genre}" : "${movie.genre}",
       |    "${MovieFields.ageRating}" : "${movie.ageRating}",
       |    "${MovieFields.img}" : "${movie.img}",
       |    "${MovieFields.description}" : "${movie.description}"
       |}
       |""".stripMargin)

  def count: Int = await(db.collection.countDocuments().toFutureOption()).get.toInt

  "POST /movie" should {
    "add in a movie into the database" when {
      await(db.collection.drop().toFuture())
      "provided valid json" in {
        count shouldBe 0
        val result = await(ws.url(s"http://localhost:$port/movie").post(json))
        result.status shouldBe CREATED
        count shouldBe 1
      }
      "providing invalid json" in {
        count shouldBe 1
        val result = await(ws.url(s"http://localhost:$port/movie").post(Json.parse("{}")))
        result.status shouldBe BAD_REQUEST
        count shouldBe 1
      }
    }
  }


}
