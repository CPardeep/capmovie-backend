package controllers

import models.Review
import models.Review.newReviewReads
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repos.ReviewRepo
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReviewController @Inject()(cc: ControllerComponents, repo: ReviewRepo) extends AbstractController(cc) {

  def create(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Review](newReviewReads) match {
      case JsSuccess(review, _) => repo.create(review).map {
        case true => Created
        case false => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }
}
