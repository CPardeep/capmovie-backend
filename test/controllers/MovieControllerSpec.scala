package controllers

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, CREATED, INTERNAL_SERVER_ERROR}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.MovieService
import utils.TestObjects.movie
import utils.MovieFields

import scala.concurrent.Future

class MovieControllerSpec extends AbstractControllerTest {

  val service: MovieService = mock[MovieService]
  val controller = new MovieController(Helpers.stubMessagesControllerComponents(), service)
  val json = Json.parse(
    s"""{
       |    "${MovieFields.name}" : "${movie.name}",
       |    "${MovieFields.year}" : ${movie.year},
       |    "${MovieFields.genre}" : "${movie.genre}",
       |    "${MovieFields.ageRating}" : "${movie.ageRating}",
       |    "${MovieFields.img}" : "${movie.img}",
       |    "${MovieFields.description}" : "${movie.description}"
       |}
       |""".stripMargin)

  "createMovie" should  {
    "succeed" when {
      "a movie is successfully added to the database" in {
        when(service.create(any())) thenReturn(Future.successful(true))
        val result = controller.create().apply(FakeRequest().withBody(json))
        status(result) shouldBe CREATED
      }
      "fail" when {
        "the service does not return a success" in {
          when(service.create(any())) thenReturn (Future.successful(false))
          val result = controller.create().apply(FakeRequest().withBody(json))
          status(result) shouldBe INTERNAL_SERVER_ERROR
        }
        "returns bad request if invalid json format is given" in {
          val result = controller.create().apply(FakeRequest().withBody(Json.parse("{}")))
          status(result) shouldBe BAD_REQUEST
        }
      }
    }
  }



}
