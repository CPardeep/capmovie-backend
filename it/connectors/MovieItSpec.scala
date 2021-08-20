package connectors

import models.Movie
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport
import utils.TestObjects

class MovieItSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[Movie] {
  override val repository = new MovieRepo(mongoComponent)

  "Create" should {
    "return true when valid details are submitted" in {
      await(repository.create(TestObjects.movie)) shouldBe true
    }
    "return false when duplicate details are submitted" in {
      await(repository.create(TestObjects.movie))
      await(repository.create(TestObjects.movie)) shouldBe false
    }
  }
}
