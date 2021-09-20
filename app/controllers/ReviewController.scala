package controllers

import models.Review
import models.Review.newReviewReads
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import services.MovieService
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repos.MovieRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewController @Inject()(cc: ControllerComponents, service: MovieService) extends AbstractController(cc) {

  def create(movieId: String): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Review](newReviewReads) match {
      case JsSuccess(body, _) => service.createReviewRating(movieId, body).map{
        case true => Created
        case _ => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }

}
