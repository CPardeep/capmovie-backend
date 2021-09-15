package controllers

import models.Review
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, CREATED, INTERNAL_SERVER_ERROR}
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.ReviewService

import scala.concurrent.Future

class ReviewControllerSpec extends AbstractControllerTest {

  val service: ReviewService = mock[ReviewService]
  val controller = new ReviewController(Helpers.stubMessagesControllerComponents(), service)

  val review: Review = Review(
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )


  "POST /review" should {
    "succeed when a movies is added to the database successfully" in {
      when(service.createReviewRating(any(), any(), any())) thenReturn Future.successful(true, true, true)
      val result = controller.create("TESTMOVIEID", "TESTUSERID").apply(FakeRequest().withBody(Json.toJson(review)))
      status(result) shouldBe CREATED
    }
    "fail" when {
      "the service does not return a success" in {
        when(service.createReviewRating(any(), any(), any())) thenReturn Future.successful(false, false, false)
        val result = controller.create("TESTMOVIEID", "TESTUSERID").apply(FakeRequest().withBody(Json.toJson(review)))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
      "invalid json is given" in {
        val result = controller.create("TESTMOVIEID", "TESTUSERID").apply(FakeRequest().withBody(Json.parse("{}")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

}
