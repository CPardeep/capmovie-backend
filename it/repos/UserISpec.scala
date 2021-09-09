package repos

import models.User
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class UserISpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[User]{
  val repository = new UserRepo(mongoComponent)
  val user: User = User(
    id = "testId",
    password = "testPass"
  )

  "Create" should {
    "return true when valid detauls are submitted" in {
      await(repository.create(user)) shouldBe true
    }
    "return false when duplicate details are submitted" in {
      await(repository.create(user))
      await(repository.create(user)) shouldBe false
    }
  }

  "read" should {
    "return an admin successfully" in {
      await(repository.create(user))
      await(repository.read(user.id)) shouldBe Some(user)
    }
    "return nothing" in {
      await(repository.read(user.id)) shouldBe None
    }
  }
}
