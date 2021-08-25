package controllers

import models.Admin
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.LoginService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AdminController @Inject()(cc: ControllerComponents,
                                loginService: LoginService)
  extends AbstractController(cc) {

  def login: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Admin] match {
      case JsSuccess(admin, _) =>
        loginService.checkMatches(admin).map {
          case true => Ok
          case false => Unauthorized
        }.recover{case _ => InternalServerError}
      case JsError(_) => Future(BadRequest)
    }
  }
}
