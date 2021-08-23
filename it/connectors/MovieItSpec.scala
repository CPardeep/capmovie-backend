package connectors

import models.Movie
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class MovieItSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[Movie] {
  override val repository = new MovieRepo(mongoComponent)
  val movie: Movie = Movie(
    id = "TESTMOV",
    plot = "Test plot",
    genres = List(
      "testGenre1",
      "testGenre2"),
    rated = "testRating",
    cast = List(
      "testPerson",
      "TestPerson"),
    poster = "testURL",
    title = "testTitle")
  "Create" should {
    "return true when valid details are submitted" in {
      await(repository.create(movie)) shouldBe true
    }
    "return false when duplicate details are submitted" in {
      await(repository.create(movie))
      await(repository.create(movie)) shouldBe false
    }
  }

  "readAll" should {
    "return a list of movies successfully" in {
      val testMovieList = List(movie, movie.copy(id= "TESMOV2"))
      testMovieList.map(x => await(repository.create(x)))
      await(repository.readAll()) shouldBe testMovieList
    }
    "returns an empty list" in {
      await(repository.readAll()) shouldBe List()
    }
  }

}
