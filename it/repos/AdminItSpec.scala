package repos

import models.User
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AdminItSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[User]{
  val repository = new AdminRepo(mongoComponent)
  val admin: User = User(
    id = "testId",
    password = "testPass"
  )
  "Create" should {
    "return true when valid detauls are submitted" in {
      await(repository.create(admin)) shouldBe true
    }
    "return false when duplicate details are submitted" in {
      await(repository.create(admin))
      await(repository.create(admin)) shouldBe false
    }
  }

  "read" should {
    "return an admin successfully" in {
      await(repository.create(admin))
      await(repository.read(admin.id)) shouldBe Some(admin)
    }
    "return nothing" in {
      await(repository.read(admin.id)) shouldBe None
    }
  }
}
