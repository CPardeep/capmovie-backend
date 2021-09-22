package controllers

import models.User
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR, OK, UNAUTHORIZED}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.LoginService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdminControllerSpec extends AbstractControllerTest {
  val service: LoginService = mock[LoginService]
  val controller: AdminController = new AdminController(Helpers.stubControllerComponents(), service)
  val testAdmin: User = User(
    id = "testID",
    password = "testPass"
  )
  "login" should {
    "return ok" when {
      "JsSuccess and matches pass" in {
        when(service.checkMatches(any()))
          .thenReturn(Future(true))
        val result = controller.login.apply(FakeRequest("POST", "/").withBody(Json.toJson(testAdmin)))
        status(result) shouldBe OK
      }
    }
    "return Unauthorized" when {
      "Js Success but matches fails" in {
        when(service.checkMatches(any()))
          .thenReturn(Future(false))
        val result = controller.login.apply(FakeRequest("POST", "/").withBody(Json.toJson(testAdmin)))
        status(result) shouldBe UNAUTHORIZED
      }
    }
    "return Bad request" when {
      "There is a Js Error" in {
        val result = controller.login.apply(FakeRequest("POST", "/").withBody(Json.toJson("{badJson}")))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return an internal server error" when {
      "database fails" in {
        when(service.checkMatches(any()))
          .thenReturn(Future.failed(new RuntimeException))
        val result = controller.login.apply(FakeRequest("POST", "/").withBody(Json.toJson(testAdmin)))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

}
