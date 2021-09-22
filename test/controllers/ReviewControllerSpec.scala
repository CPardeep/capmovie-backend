package controllers

import models.Review
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.MovieService

import scala.concurrent.Future

class ReviewControllerSpec extends AbstractControllerTest {

  val service: MovieService = mock[MovieService]
  val controller = new ReviewController(Helpers.stubMessagesControllerComponents(), service)

  val review: Review = Review(
    movieId = None,
    userId = "TESTUSER",
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )

  "POST /review" should {
    "succeed when a review is added to the database successfully" in {
      when(service.createReview(any(), any())) thenReturn Future.successful(true)
      val result = controller.create("TESTMOVIEID").apply(FakeRequest().withBody(Json.toJson(review)))
      status(result) shouldBe CREATED
    }
    "fail" when {
      "the service does not return a success" in {
        when(service.createReview(any(), any())) thenReturn Future.successful(false)
        val result = controller.create("TESTMOVIEID").apply(FakeRequest().withBody(Json.toJson(review)))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
      "invalid json is given" in {
        val result = controller.create("TESTMOVIEID").apply(FakeRequest().withBody(Json.parse("{}")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

  "GET /review/:id" should {
    "returns OK when all reviews are found associated to the movie" in {
      when(service.readReviewByID(any())) thenReturn Future.successful(List(review))
      val result = controller.readReviewById("TESTUSER").apply(FakeRequest())
      status(result) shouldBe OK
    }
    "returns NOT FOUND if ID does not exist with an review" in {
      when(service.readReviewByID(any())) thenReturn Future.successful(Nil)
      val result = controller.readReviewById("TESTUSER").apply(FakeRequest())
      status(result) shouldBe NOT_FOUND
    }
  }

  "PATCH /movie/:movieId/review/update" should {
    "returns Created when a valid review update is given" in {
      when(service.updateReview(any(), any())) thenReturn Future.successful(true)
      val result = controller.update("TESTMOVIEID").apply(FakeRequest().withBody(Json.toJson(review)))
      status(result) shouldBe CREATED
    }
    "returns Internal server error when a valid movie id or user id is not given" in {
      when(service.updateReview(any(), any())) thenReturn Future.successful(false)
      val result = controller.update("TESTMOVIEID").apply(FakeRequest().withBody(Json.toJson(review)))
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
    "returns Badrequest when invalid json is given" in {
      val result = controller.update("TESTMOVIEID").apply(FakeRequest().withBody(Json.toJson("")))
      status(result) shouldBe BAD_REQUEST
    }
  }

  "PATCH /movie/:movieId/review/:userId/delete" should {
    "returns No cotent when valid movie id and user id is given for an existing review" in {
      when(service.removeReview(any(), any())) thenReturn Future.successful(true)
      val result = controller.remove("TESTMOVIEID", "TESTUSERID").apply(FakeRequest())
      status(result) shouldBe NO_CONTENT
    }
    "returns Internal server error when a invlaid details are given for the review" in {
      when(service.removeReview(any(), any())) thenReturn Future.successful(false)
      val result = controller.remove("TESTMOVIEID", "TESTUSERID").apply(FakeRequest())
      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
  }


}
