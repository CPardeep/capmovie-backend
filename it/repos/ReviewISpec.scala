package repos

import models.Review
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class ReviewISpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[Review] {
  val repository = new ReviewRepo(mongoComponent)

  val review: Review = Review(
    userId = "TESTUSER",
    movieId = "TESTMOVIE",
    review = "TESTREVIEW",
    rating = 5.0,
    isApproved = false
  )

  "Create" should {
    "return true when a valid review is entered in db" in {
      await(repository.create(review)) shouldBe true
    }
    "returns false when a duplicate is being entered into database" in {
      await(repository.create(review))
      await(repository.create(review)) shouldBe false
    }
  }
}
