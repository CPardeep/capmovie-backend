package controllers

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR, OK}
import play.api.libs.json.{JsObject, Json}
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.MovieRepo

import scala.concurrent.Future

class UpdateControllerSpec extends AbstractControllerTest {

  val repo: MovieRepo = mock[MovieRepo]
  val controller = new UpdateController(Helpers.stubControllerComponents(), repo)

  val movieTitle: JsObject = Json.obj("title" -> "testTitle")
  val movieGenre: JsObject = Json.obj("genres" -> "testGenre")
  val movieRating: JsObject = Json.obj("rated" -> "testRating")
  val moviePlot: JsObject = Json.obj("plot" -> "testPlot")
  val moviePoster: JsObject = Json.obj("poster" -> "testPoster")
  val movieCast: JsObject = Json.obj("cast" -> "testCast")

  "PATCH updateTitle" should {
    "return OK" when {
      "the jsvalue is valid and update is successful" in {
        when(repo.updateTitle(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updateTitle("TESTID")
          .apply(FakeRequest("PATCH", "/update-title")
            .withBody(Json.toJson(movieTitle)))
        status(result) shouldBe OK
      }
    }
    "return BAD REQUEST" when {
      "the update is unsuccessful" in {
        when(repo.updateTitle(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updateTitle("TESTID")
          .apply(FakeRequest("PATCH", "/update-title")
            .withBody(Json.toJson(movieTitle)))
        status(result) shouldBe BAD_REQUEST
      }
    }

    "return INTERNAL SERVER ERROR" when {
      "the jsValue is not valid" in {
        val result = controller.updateTitle("TESTID")
          .apply(FakeRequest("PATCH", "/update-title")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH updateGenre" should {
    "return Ok" when {
      "the JsValue is valid and update is successful" in {
        when(repo.updateGenre(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updateGenre("TESTID")
          .apply(FakeRequest("PATCH", "/update-genre")
            .withBody(Json.toJson(movieGenre)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the update is unsuccessful" in {
        when(repo.updateGenre(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updateGenre("TESTID")
          .apply(FakeRequest("PATCH", "/update-genre")
            .withBody(Json.toJson(movieGenre)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.updateGenre("TESTID")
          .apply(FakeRequest("PATCH", "/update-genre")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH updateAgeRating" should {
    "return Ok" when {
      "the JsValue is valid and update is successful" in {
        when(repo.updateAgeRating(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updateAgeRating("TESTID")
          .apply(FakeRequest("PATCH", "/update-age-rating")
            .withBody(Json.toJson(movieRating)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the update is unsuccessful" in {
        when(repo.updateAgeRating(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updateAgeRating("TESTID")
          .apply(FakeRequest("PATCH", "/update-age-rating")
            .withBody(Json.toJson(movieRating)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.updateAgeRating("TESTID")
          .apply(FakeRequest("PATCH", "/update-age-rating")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH updatePlot" should {
    "return Ok" when {
      "the JsValue is valid and update is successful" in {
        when(repo.updatePlot(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updatePlot("TESTID")
          .apply(FakeRequest("PATCH", "/update-plot")
            .withBody(Json.toJson(moviePlot)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the update is unsuccessful" in {
        when(repo.updatePlot(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updatePlot("TESTID")
          .apply(FakeRequest("PATCH", "/update-plot")
            .withBody(Json.toJson(moviePlot)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.updatePlot("TESTID")
          .apply(FakeRequest("PATCH", "/update-plot")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH updatePoster" should {
    "return Ok" when {
      "the JsValue is valid and update is successful" in {
        when(repo.updatePoster(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updatePoster("TESTID")
          .apply(FakeRequest("PATCH", "/update-post")
            .withBody(Json.toJson(moviePoster)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the update is unsuccessful" in {
        when(repo.updatePoster(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updatePoster("TESTID")
          .apply(FakeRequest("PATCH", "/update-post")
            .withBody(Json.toJson(moviePoster)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.updatePoster("TESTID")
          .apply(FakeRequest("PATCH", "/update-post")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH updateCast" should {
    "return Ok" when {
      "the JsValue is valid and update is successful" in {
        when(repo.updateCast(any(), any())).thenReturn(Future.successful(true))
        val result = controller.updateCast("TESTID")
          .apply(FakeRequest("PATCH", "/update-cast")
            .withBody(Json.toJson(movieCast)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the update is unsuccessful" in {
        when(repo.updateCast(any(), any())).thenReturn(Future.successful(false))
        val result = controller.updateCast("TESTID")
          .apply(FakeRequest("PATCH", "/update-cast")
            .withBody(Json.toJson(movieCast)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.updateCast("TESTID")
          .apply(FakeRequest("PATCH", "/update-cast")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH removeGenre" should {
    "return Ok" when {
      "the jsValue is valid and remove is successful" in {
        when(repo.removeGenre(any(), any()))
          .thenReturn(Future.successful(true))
        val result = controller.removeGenre("TESTID")
          .apply(FakeRequest("PATCH", "/remove-genre")
            .withBody(Json.toJson(movieGenre)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the removal is unsuccessful" in {
        when(repo.removeGenre(any(), any())).thenReturn(Future.successful(false))
        val result = controller.removeGenre("TESTID")
          .apply(FakeRequest("PATCH", "/remove-genre")
            .withBody(Json.toJson(movieGenre)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.removeGenre("TESTID")
          .apply(FakeRequest("PATCH", "/remove-genre")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH removeCast" should {
    "return Ok" when {
      "the jsValue is valid and remove is successful" in {
        when(repo.removeCast(any(), any()))
          .thenReturn(Future.successful(true))
        val result = controller.removeCast("TESTID")
          .apply(FakeRequest("PATCH", "/remove-cast")
            .withBody(Json.toJson(movieCast)))
        status(result) shouldBe OK
      }
    }
    "return BadRequest" when {
      "the removal is unsuccessful" in {
        when(repo.removeCast(any(), any())).thenReturn(Future.successful(false))
        val result = controller.removeCast("TESTID")
          .apply(FakeRequest("PATCH", "/remove-cast")
            .withBody(Json.toJson(movieCast)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return InternalServerError" when {
      "the jsValue is not valid" in {
        val result = controller.removeCast("TESTID")
          .apply(FakeRequest("PATCH", "/remove-cast")
            .withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

}
