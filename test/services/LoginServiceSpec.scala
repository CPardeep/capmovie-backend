package services

import models.User
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.{AdminRepo, UserRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LoginServiceSpec extends AnyWordSpec with Matchers with MockitoSugar {
  val adminRepo: AdminRepo = mock[AdminRepo]
  val userRepo: UserRepo = mock[UserRepo]
  val service: LoginService = new LoginService(adminRepo, userRepo)
  val user: User = User(
    id = "testId",
    password = "testPass")

  "Check matches" should {
    "return true" when {
      "request id and id in database match" in {
        when(adminRepo.read(any()))
          .thenReturn(Future(Some(user)))
        await(service.checkMatches(user)) shouldBe true
      }
    }
    "return false" when {
      "when passwords do not match" in {
        when(adminRepo.read(any()))
          .thenReturn(Future(Some(user)))
        await(service.checkMatches(user.copy(password = "badPass"))) shouldBe false
      }
      "no admin returned from db" in {
        when(adminRepo.read(any()))
          .thenReturn(Future(None))
        await(service.checkMatches(user)) shouldBe false
      }
    }
  }

  "Check matches users" should {
    "return true" when {
      "request id and id in database match" in {
        when(userRepo.read(any()))
          .thenReturn(Future(Some(user)))
        await(service.checkMatchesUser(user)) shouldBe true
      }
    }
    "return false" when {
      "when passwords do not match" in {
        when(userRepo.read(any()))
          .thenReturn(Future(Some(user)))
        await(service.checkMatchesUser(user.copy(password = "badPass"))) shouldBe false
      }
      "no admin returned from db" in {
        when(userRepo.read(any()))
          .thenReturn(Future(None))
        await(service.checkMatchesUser(user)) shouldBe false
      }
    }
  }
}
