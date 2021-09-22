package controllers

import models.Review
import models.Review.newReviewReads
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.MovieService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewController @Inject()(cc: ControllerComponents, service: MovieService) extends AbstractController(cc) {

  def create(movieId: String): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Review](newReviewReads) match {
      case JsSuccess(body, _) => service.createReview(movieId, body).map {
        case true => Created
        case _ => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }

  def readReviewById(userId: String): Action[AnyContent] = Action async {
    service.readReviewByID(userId).map {
      case review if review.nonEmpty => Ok(Json.toJson(review))
      case _ => NotFound
    }
  }

  def update(movieId: String): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Review](newReviewReads) match {
      case JsSuccess(body, _) => service.updateReview(movieId, body).map {
        case true => Created
        case _ => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }

  def remove(movieId: String, userId: String): Action[AnyContent] = Action.async {
    service.removeReview(movieId, userId).map {
      case true => NoContent
      case false => InternalServerError
    }
  }

}
