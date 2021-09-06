package connectors

import models.Movie
import org.scalatestplus.play.guice.{GuiceOneAppPerSuite, GuiceOneServerPerSuite}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.MovieRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class MovieItSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[Movie] {
  val repository = new MovieRepo(mongoComponent)
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
  "Read" should {
    "return a movie successfully" in {
      await(repository.create(movie))
      val result = await(repository.read(movie.id))
      result shouldBe Some(movie)
    }
    "return nothing" in {
      await(repository.read(movie.id)) shouldBe None
    }
  }
  "readAll" should {
    "return a list of movies successfully" in {
      await(repository.create(movie))
      await(repository.create(movie.copy(id = "TESTMOV2")))
      await(repository.readAll()).size shouldBe 2
    }
    "returns an empty list" in {
      await(repository.readAll()) shouldBe List()
    }
    "Delete" should {
      "return true when deleted successfully" in {
        await(repository.create(movie))
        await(repository.delete(movie.id)) shouldBe true
      }
      "return false when id doesnt exist" in {
        await(repository.delete("badID")) shouldBe false
      }
    }
  }

}
