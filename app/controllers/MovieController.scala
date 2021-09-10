package controllers

import models.Movie
import models.Movie.newMovieReads
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repos.MovieRepo
import services.MovieService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class MovieController @Inject()(cc: ControllerComponents, service: MovieService, movieRepo: MovieRepo) extends AbstractController(cc) {

  def readAll(): Action[AnyContent] = Action.async { implicit request =>
    movieRepo.readAll().map { movie => Ok(Json.toJson(movie)) }
      .recover { case _ => BadRequest }
  }

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    movieRepo.read(id).map {
      case Some(movie) => Ok(Json.toJson(movie))
      case None => NotFound
    }.recover { case _ => BadRequest }
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
  def delete(id: String): Action[AnyContent] = Action.async { implicit request =>
    movieRepo.delete(id).map {
      case true => NoContent
      case false => NotFound
    }
  }
}
