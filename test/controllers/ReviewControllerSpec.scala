package controllers

import models.Review
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, CREATED, INTERNAL_SERVER_ERROR, NOT_FOUND, NO_CONTENT}
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.ReviewRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewControllerSpec extends AbstractControllerTest {

  val repo: ReviewRepo = mock[ReviewRepo]
  val controller = new ReviewController(Helpers.stubMessagesControllerComponents(), repo)

  val review: Review = Review(
    userId = "TESTUSER",
    movieId = "TESTMOVIE",
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )


  "POST /review" should {
    "succeed when a movies is added to the database successfully" in {
      when(repo.create(any())) thenReturn Future.successful(true)
      val result = controller.create().apply(FakeRequest().withBody(Json.toJson(review)))
      status(result) shouldBe CREATED
    }
    "fail" when {
      "the repo does not return a success" in {
        when(repo.create(any())) thenReturn Future.successful(false)
        val result = controller.create().apply(FakeRequest().withBody(Json.toJson(review)))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
      "invalid json is given" in {
        val result = controller.create().apply(FakeRequest().withBody(Json.parse("{}")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

  "DELETE /delete/userid/review/movieId" should {
    "return noContent when review is successfully deleted" in {
      when(repo.deleteReview(any(), any()))
        .thenReturn(Future(true))
      val result = controller.delete("TESTMOVIE", "TESTUSER").apply(FakeRequest())
      status(result) shouldBe NO_CONTENT
    }
    "return NotFound when userId does not exist and delete is unsuccessful" in {
      when(repo.deleteReview(any(), any()))
        .thenReturn(Future(false))
      val result = controller.delete("TESTMOVIE", "BADID").apply(FakeRequest())
      status(result) shouldBe NOT_FOUND
    }
  }

}
