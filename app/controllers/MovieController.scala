package controllers

import models.Movie
import models.Movie.newMovieReads
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.MovieService
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MovieController @Inject() (cc: ControllerComponents, service: MovieService) extends AbstractController(cc){

  def create(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Movie](newMovieReads) match {
      case JsSuccess(x, _) => service.create(x).map{
        case true => Created
        case false => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }




}
