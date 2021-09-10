package controllers

import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repos.MovieRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UpdateController @Inject()(cc: ControllerComponents,
                                 movieRepo: MovieRepo) extends AbstractController(cc) {

  def updateTitle(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "title").as[String]
    } match {
      case Success(json) => movieRepo.updateTitle(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def updateGenre(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "genres").as[String]
    } match {
      case Success(json) => movieRepo.updateGenre(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def updateAgeRating(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "rated").as[String]
    } match {
      case Success(json) => movieRepo.updateAgeRating(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def updatePlot(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "plot").as[String]
    } match {
      case Success(json) => movieRepo.updatePlot(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def updatePoster(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "poster").as[String]
    } match {
      case Success(json) => movieRepo.updatePoster(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def updateCast(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "cast").as[String]
    } match {
      case Success(json) => movieRepo.updateCast(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def removeGenre(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "genres").as[String]
    } match {
      case Success(json) => movieRepo.removeGenre(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }

  def removeCast(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "cast").as[String]
    } match {
      case Success(json) => movieRepo.removeCast(id, json).map {
        case true => Ok
        case false => BadRequest
      }
      case Failure(_) => Future.successful(InternalServerError)
    }
  }


}
