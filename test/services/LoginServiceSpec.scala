package services

import models.Admin
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AdminRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class LoginServiceSpec extends AnyWordSpec with Matchers with MockitoSugar {
  val repo: AdminRepo = mock[AdminRepo]
  val service: LoginService = new LoginService(repo)
  val testAdmin: Admin = Admin(
    id = "testId",
    password = "testPass")
  "Check matches" should {
    "return true" when {
      "request id and id in database match" in {
        when(repo.read(any()))
          .thenReturn(Future(Some(testAdmin)))
        await(service.checkMatches(testAdmin)) shouldBe true
      }
    }
    "return false" when {
      "when passwords do not match" in {
        when(repo.read(any()))
          .thenReturn(Future(Some(testAdmin)))
        await(service.checkMatches(testAdmin.copy(password="badPass"))) shouldBe false
      }
      "no admin returned from db" in {
        when(repo.read(any()))
          .thenReturn(Future(None))
        await(service.checkMatches(testAdmin)) shouldBe false
      }
    }
  }
}
