package controllers

import models.Movie
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, CREATED, INTERNAL_SERVER_ERROR, NOT_FOUND, OK}
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.MovieRepo
import services.MovieService
import utils.MovieFields

import scala.concurrent.Future

class MovieControllerSpec extends AbstractControllerTest {

  val service: MovieService = mock[MovieService]
  val repo = mock[MovieRepo]
  val controller = new MovieController(Helpers.stubMessagesControllerComponents(), service, repo)
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
  val json: JsValue = Json.parse(
    s"""{
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
  val testMovieList = List(movie, movie.copy(id= "TESMOV2"))

  "createMovie" should {
    "succeed" when {
      "a movie is successfully added to the database" in {
        when(service.create(any())) thenReturn (Future.successful(true))
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

  "readAll" should {
    "return OK with list of movies" in {
      when(repo.readAll())
        .thenReturn(Future.successful(testMovieList))
      val result = controller.readAll().apply(FakeRequest())
      status(result) shouldBe OK
    }

    "return a badRequest" in {
      when(repo.readAll())
        .thenReturn(Future.failed(new RuntimeException))
      val result = controller.readAll().apply(FakeRequest())
      status(result) shouldBe BAD_REQUEST
    }
  }

  "read" should {
    "return OK with a movie" in {
      when(repo.read(any()))
        .thenReturn(Future.successful(Some(movie)))
      val result = controller.read(movie.id).apply(FakeRequest())
      status(result) shouldBe OK
    }
    "return not found" in {
      when(repo.read(any()))
        .thenReturn(Future.successful(None))
      val result = controller.read(movie.id).apply(FakeRequest())
      status(result) shouldBe NOT_FOUND
    }
    "return BadRequest" in {
      when(repo.read(any()))
        .thenReturn(Future.failed(new RuntimeException))
      val result = controller.read(movie.id).apply(FakeRequest())
      status(result) shouldBe BAD_REQUEST
    }
  }

}
