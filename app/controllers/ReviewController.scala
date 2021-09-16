package controllers

import models.Review
import models.Review.newReviewReads
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.ReviewService
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repos.ReviewRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewController @Inject()(cc: ControllerComponents, service: ReviewService) extends AbstractController(cc) {

  def create(movieId: String, userId: String): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Review](newReviewReads) match {
      case JsSuccess(body, _) => service.createReviewRating(movieId, userId, body).map{
        case (true, true, true) => Created
        case _ => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }

  }

  def delete(movieId: String, userId: String): Action[AnyContent] = Action.async { implicit request =>
    repo.deleteReview(movieId, userId).map {
      case true => NoContent
      case false => NotFound
    }
  }
}
