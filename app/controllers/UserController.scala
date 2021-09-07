package controllers

import models.User
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import services.LoginService
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class UserController @Inject()(cc: ControllerComponents,
                               loginService: LoginService)
  extends AbstractController(cc) {

  def login: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[User] match {
      case JsSuccess(user, _) =>
        loginService.checkMatchesUser(user).map {
          case true => Ok
          case false => Unauthorized
        }.recover{case _ => InternalServerError}
      case JsError(_) => Future(BadRequest)
    }
  }

}
