package services

import models.Admin
import repos.AdminRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LoginService @Inject()(adminRepo: AdminRepo) {

  def checkMatches(requestedAdmin: Admin): Future[Boolean] = {
    adminRepo.read(requestedAdmin.id).map {
      case Some(admin) => admin.password == requestedAdmin.password
      case _ => false
    }
  }
}
