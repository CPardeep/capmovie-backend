package controllers

import models.Movie
import models.Movie.newMovieReads
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.MovieService
import repos.MovieRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MovieController @Inject()(cc: ControllerComponents, service: MovieService, movieRepo: MovieRepo) extends AbstractController(cc) {

  def readAll(): Action[AnyContent] = Action.async { implicit request =>
    movieRepo.readAll().map{movie => Ok(Json.toJson(movie))}
      .recover {case _ => BadRequest}
  }

  def create(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Movie](newMovieReads) match {
      case JsSuccess(x, _) => service.create(x).map {
        case true => Created
        case false => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }

}
