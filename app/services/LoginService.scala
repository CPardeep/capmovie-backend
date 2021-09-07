package services

import models.User
import repos.{AdminRepo, UserRepo}
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LoginService @Inject()(adminRepo: AdminRepo, userRepo: UserRepo) {

  def checkMatches(requestedAdmin: User): Future[Boolean] = {
    adminRepo.read(requestedAdmin.id).map {
      case Some(admin) => admin.password == requestedAdmin.password
      case _ => false
    }
  }

  def checkMatchesUser(requestedUser: User): Future[Boolean] = {
    userRepo.read(requestedUser.id).map {
      case Some(user) => user.password == requestedUser.password
      case _ => false
    }
  }
}
